#!/usr/bin/env python
#
# Builder -- android cli compiler
# (C) Alan Schneider <aschneider@dismoiou.fr>
#
# Architecture:
# ./
#   libs/
#        actionbarsherlock/
#        ...
#   res/
#   src/
#   AndroidManifest.xml
#   builder*
#

import argparse
import commands
import shutil
import glob
import sys
import os
import re

# imitates cat
def __read(filename):
    with open(filename) as f:
        return f.readlines()

# imitates cut
def __cut(line, separator, count):
    groups = line.split(separator)
    return groups[count - 1] if len(groups) >= count else None

# imitates grep
def __grep(match, lines):
    grep = []
    for line in lines:
        if match in line:
            grep.append(line.strip())
    return grep

# imitates find
def __find(directory, filename):
    for root, dirs, files in os.walk(directory):
        for f in files:
            if os.path.basename(f) == filename:
                return os.path.join(root, f)

# android binary
def _binAndroid(androidSdk):
    binAndroid = '%s/tools/android' % (androidSdk)
    return binAndroid if os.path.isfile(binAndroid) else None

# adb binary
def _binAdb(androidSdk):
    binAdb = '%s/platform-tools/adb' % (androidSdk)
    return binAdb if os.path.isfile(binAdb) else None

# application version from manifest
def _applicationVersion(applicationManifest):
    return [int(__cut(__grep('android:versionCode=', __read(applicationManifest))[0], '"', 2)),
            __cut(__grep('android:versionName=', __read(applicationManifest))[0], '"', 2)]

# support library from sdk
def _androidSupport(androidSdk):
    androidSupport = '%s/extras/android/support/v4/android-support-v4.jar' % (androidSdk)
    return androidSupport if os.path.isfile(androidSupport) else None

# inactive debug ant properties
def _antPropertiesDebug(workingDirectory):
    antPropertiesDebug = '%s/ant.debug' % (workingDirectory)
    return antPropertiesDebug if os.path.isfile(antPropertiesDebug) else None

# inavtive release ant properties
def _antPropertiesRelease(workingDirectory):
    antPropertiesRelease = '%s/ant.release' % (workingDirectory)
    return antPropertiesRelease if os.path.isfile(antPropertiesRelease) else None

# executes shell command
def _run(cmd):
    (status, output) = commands.getstatusoutput(cmd)
    if status:
        print 'Error: command \'%s\' failed with code #%d' % (cmd.split()[0], status)
        sys.exit(1)
    return output

# log h1
def _(s):
    print '=>', s

# log h2
def __(s):
    print '-', s

# log h3
def ___(s):
    print ' ', s

# infos
def infos(c):
    _('Infos')
    __('Directory')
    ___(c['workingDirectory'])
    __('Manifest')
    ___(c['application']['manifest'])
    ___(c['application']['package'])
    ___('API-%d' % (c['application']['api']))
    ___('v%s b%d' % (c['application']['version'][1], c['application']['version'][0]))
    __('SDK')
    ___(c['android']['sdk'])
    ___(c['android']['target'])
    if len(c['android']['support']):
        ___('sdk:%s' % (c['android']['support'][len(c['android']['sdk']):]))
    __('Device')
    if len(c['device']):
        ___(c['device'])

# updates source tree
def update(c):
    _('Update')

    # submodules
    if os.path.isfile('.gitmodules'):
        __('git:submodule')
        _run('git submodule update --init')

    # support library
    if len(c['android']['support']):
        __('support:%s' % (c['android']['support'][len(c['android']['sdk']):]))
        shutil.copy(c['android']['support'], 'libs/')

    __('libs:project')
    # library projects
    for libraryPath in __grep('android.library.reference', __read('project.properties')):
        libraryPath = __cut(libraryPath, '=', 2)
        ___(libraryPath)
        _run('%s update lib-project --target "%s" --path %s' % (c['bin']['android'], c['android']['target'], libraryPath))
    # jars
    __('libs:jar')
    for jar in glob.glob('libs/*.jar'):
        ___(jar)
        for libraryPath in __grep('android.library.reference', __read('project.properties')):
            libraryPath = __cut(libraryPath, '=', 2)
            if not os.path.isdir('%s/libs' % (libraryPath)):
                os.mkdir('%s/libs' % (libraryPath))
            shutil.copy(jar, '%s/libs/' % (libraryPath))
    # root project
    __('.')
    _run('%s update project --name "%s" --target "%s" --path .' % ('%s %s' % (c['bin']['android'], c['bin']['android']), c['application']['package'], c['android']['target']))

# lint checks
def check(c):
    _('Check')
    _run('lint --quiet -w -Xlint:deprecation .')

# builds debug or release
def _build(t, c):
    _(t.title())
    __(c['antProperties'])
    if os.path.isfile(c['antProperties%s' % (t.title())]):
        shutil.copy(c['antProperties%s' % (t.title())], c['antProperties'])
    __('ant %s' % (t))
    _run('%s -logfile build.log %s' % (c['bin']['ant'], t))
    __('sign')
    shutil.copy(c['apk%s' % (t.title())], c['apk'])
    _(c['apk'])

# builds debug
def debug(c):
    _build('debug', c)

# builds release
def release(c):
    _build('release', c)

# generates documentation
def doc(c):
    _('Doc')
    if not os.path.isdir('%s/docs/reference' % (c['android']['sdk'])):
        print 'Error: no documentation'
        return
    _run('%s --quiet -protected -splitindex -d out' % (c['bin']['javadoc']))

# cleans all
def clean(c):
    _('Clean')
    __('ant clean')
    _run('ant clean')
    if os.path.isdir('doc'):
        __('doc')
        _run('rm -rf doc')
    if os.path.isdir('out'):
        __('out')
        _run('rm -rf out')
    if os.path.isfile('build.log'):
        __('build.log')
        _run('rm -rf build.log')
    if os.path.isfile(c['apk']):
        __(c['apk'])
        _run('rm -f %s' % (c['apk']))

# installs apk on device
def install(c):
    _('Install')
    if not len(c['device']):
        print 'Warning: no device'
        return
    __(c['device'])
    __(c['apk'])
    _run('%s install -r -d %s' % (c['bin']['adb'], c['apk']))

# runs default activity on device
def run(c):
    _('Run')
    if not len(c['device']):
        print 'Warning: no device'
        return
    __(c['device'])
    packageActivity = '%s/%s' % (c['application']['package'], c['application']['activity'])
    __(packageActivity)
    _run('adb shell am start -n %s' % (packageActivity))

# uninstalls apk on device
def uninstall(c):
    _('Uninstall')
    if not len(c['device']):
        print 'Warning: no device'
        return
    __(c['device'])
    _run('adb shell pm uninstall %s' % (c['application']['package']))

# runs adb logcat focused on our apk
def log(c):
    _('Log')
    if not len(c['device']):
        print 'Warning: no device'
        return
    __(c['device'])
    _run('adb logcat -s %s | egrep "^[A-Z]"' % (c['application']['package']))

# main
def main():
    # argv parser
    parser = argparse.ArgumentParser(prog=os.path.basename(sys.argv[0]),
                                     description='%(prog)s: android cli compiler',
                                     add_help=True)
    parser.add_argument('--sdk',     dest='sdk',     type=str,            default=os.getenv('ANDROID_HOME'))
    #parser.add_argument('--verbose', dest='verbose', action='store_true', default=False)
    parser.add_argument('argv',                                nargs='+', help='update check debug release clean install run uninstall log')
    args = parser.parse_args()

    # usage error
    if not len(sys.argv) > 1:
        print 'Usage: %s' % (sys.argv[0]),
        for argv in FUNCTIONS:
            print argv,
        sys.exit(1)

    # config
    if not args.sdk or not len(args.sdk) or not os.path.isdir(args.sdk) or not os.path.isfile('%s/tools/android' % (args.sdk)):
        print 'Error: Android SDK not found'
        sys.exit(1)

    c = {}
    c['android'] = {}
    c['android']['sdk'] = args.sdk

    applicationManifest = __find('.', 'AndroidManifest.xml')
    if not applicationManifest or not len(applicationManifest):
        print 'Error: AndroidManifest.xml not found'
        sys.exit(1)

    applicationPackage = __cut(__grep('package=', __read(applicationManifest))[0], '"', 2)
    c['workingDirectory'] = os.path.dirname(os.path.realpath(applicationManifest))
    c['device'] = _run("%s/platform-tools/adb devices -l 2>/dev/null | egrep '^[0-9A-F]' | head -1 | cut -c1-17" % (c['android']['sdk']))
    c['antProperties'] = 'ant.properties'
    c['antPropertiesDebug'] = _antPropertiesDebug(c['workingDirectory'])
    c['antPropertiesRelease'] = _antPropertiesRelease(c['workingDirectory'])
    c['apk'] = '%s.apk' % (applicationPackage)
    c['apkDebug'] = 'bin/%s-debug.apk' % (applicationPackage)
    c['apkRelease'] = 'bin/%s-release.apk' % (applicationPackage)
    c['bin'] = {'android': _binAndroid(c['android']['sdk']),
                'adb': _binAdb(c['android']['sdk']),
                'ant': _run('which ant'),
                'lint': _run('which lint'),
                'javadoc': _run('which javadoc')}
    c['android'] = {'sdk': args.sdk, #
                    'support': _androidSupport(c['android']['sdk']),
                    'target': _run("%s/tools/android list targets -c 2>/dev/null | tail -1" % (c['android']['sdk'])),
                    'api': int(_run("%s/tools/android list targets -c 2>/dev/null | egrep '^android-' | tail -1 | cut -d'-' -f2" % (c['android']['sdk'])))}
    c['application'] = {'manifest': applicationManifest, #
                        'activity': __cut(__grep('Activity', __grep('android:name=', __read(applicationManifest)))[0], '"', 2),
                        'api': int(__cut(__grep('android:minSdkVersion=', __read(applicationManifest))[0], '"', 2)),
                        'package': applicationPackage, #
                        'version': _applicationVersion(applicationManifest)}

    functions = {'infos': infos,
                 'update': update,
                 'check': check,
                 'debug': debug,
                 'release': release,
                 'clean': clean,
                 'install': install,
                 'run': run,
                 'uninstall': uninstall,
                 'log': log}

    os.chdir(c['workingDirectory'])
    infos(c)
    for argv in args.argv:
        try:
            if functions[argv]:
                functions[argv](c)
        except KeyError:
            print 'Error: bad parameter \'%s\'' % (argv)
            parser.print_help()
            sys.exit(1)
            continue

if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        print
        sys.exit(1)
    sys.exit(0)

# EOF
