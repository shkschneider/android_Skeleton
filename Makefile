# Manifest
MANIFEST = $(shell find . -type f -name "AndroidManifest.xml" 2>/dev/null | sort | head -1)
ACTIVITY = $(shell cat "$(MANIFEST)" 2>/dev/null | tr "[:space:]" "\n" | grep 'android:name=' | grep Activity | head -1 | cut -d'"' -f2)
MIN_API = $(shell cat $(MANIFEST) 2>/dev/null | tr "[:space:]" "\n" | grep 'minSdkVersion=' | cut -d'"' -f2)
PACKAGE = $(shell cat $(MANIFEST) 2>/dev/null | tr "[:space:]" "\n" | grep 'package=' | cut -d'"' -f2)
VERSION_NAME = $(shell cat $(MANIFEST) 2>/dev/null | tr "[:space:]" "\n" | grep 'versionName=' | cut -d'"' -f2)
VERSION_CODE = $(shell cat $(MANIFEST) 2>/dev/null | tr "[:space:]" "\n" | grep 'versionCode=' | cut -d'"' -f2)

# SDK
ANDROID_SDK = ${ANDROID_HOME}
SUPPORT = $(shell find $(ANDROID_SDK)/extras/android/support/v4 -maxdepth 1 -type f -name "android-support-v4.jar" 2>/dev/null | head -1)
TARGET = $(shell $(ANDROID) list targets -c 2>/dev/null | tail -1)
LATEST_API = $(shell $(ANDROID) list targets -c 2>/dev/null | egrep '^android-' | tail -1 | cut -d'-' -f2)
DEVICE = $(shell $(ADB) devices -l 2>/dev/null | egrep '^[0-9A-F]' | head -1 | cut -c1-17)

# Commands
ANDROID = $(shell which android 2>/dev/null)
ANDROID_OPTS = --silent
ADB = $(shell which adb 2>/dev/null)
ADB_OPTS =
ANT = $(shell which ant 2>/dev/null)
ANT_LOG = "build.log"
ANT_OPTS = -logfile $(ANT_LOG)
LINT = $(shell which lint 2>/dev/null)
LINT_OPTS = --quiet -w -Xlint:deprecation
JAVADOC = $(shell which javadoc 2>/dev/null)
JAVADOC_OPTS = -quiet -protected -splitindex -d $(DOC)

# Local
DIR = $(shell dirname $(shell readlink -f "$(MANIFEST)" 2>/dev/null) 2>/dev/null)
SIGN = $(shell basename $(shell find $(DIR) -maxdepth 1 -type f -name ant.properties 2>/dev/null) 2>/dev/null)
ANT_PROPERTIES = "ant.properties"
ANT_DEBUG = "ant.debug"
ANT_RELEASE = "ant.release"
ANT_PROPERTIES_DEBUG = $(shell find . -maxdepth 1 -name $(ANT_DEBUG) 2>/dev/null)
ANT_PROPERTIES_RELEASE = $(shell find . -maxdepth 1 -name $(ANT_RELEASE) 2>/dev/null)
APK = $(PACKAGE).apk
APK_DEBUG = bin/$(PACKAGE)-debug.apk
APK_RELEASE = bin/$(PACKAGE)-release.apk
OUT = "out"
DOC = "doc"

all:
	@if [ -z "$(DIR)" ] ; then echo "Fatal error" >&2 ; exit 1 ; fi
	@echo "==> Directory"
	@echo "- $(DIR)"
	@cd $(DIR) > /dev/null || exit 1
	@echo "==> Manifest"
	@if [ -z "$(MANIFEST)" ] ; then echo "Error: no manifest" >&2 ; exit 1 ; fi
	@echo "- $(MANIFEST)"
	@if [ -z "$(PACKAGE)" ] ; then echo "Error: no package" >&2 ; exit 1 ; fi
	@echo "- $(PACKAGE)"
	@if [ -z "$(MIN_API)" ] ; then echo "Error: no api" >&2 ; exit 1 ; fi
	@echo "- API-$(MIN_API)"
	@if [ -z "$(VERSION_NAME)" -o -z "$(VERSION_CODE)" ] ; then echo "Error: no version" >&2 ; exit 1 ; fi
	@echo "- v$(VERSION_NAME) b$(VERSION_CODE)"
	@echo "==> SDK"
	@if [ -z "$(ANDROID_SDK)" ] ; then echo "Error: no sdk" >&2 ; exit 1 ; fi
	@echo "- $(ANDROID_SDK)"
	@if [ -z "$(TARGET)" ] ; then echo "Error: no target" >&2 ; exit 1 ; fi
	@echo "- $(TARGET)"
	@if [ -z "$(SUPPORT)" ] ; then echo "Error: no support library" >&2 ; exit 1 ; fi
	@echo "- sdk:$(shell echo $(SUPPORT) | sed -r 's#$(ANDROID_SDK)/##')"
	@echo "==> Device"
	@if [ -n "$(DEVICE)" ] ; then echo "- $(DEVICE)" ; fi
	@echo "==> Binaries"
	@echo "- android"
	@if [ -z "$(ANDROID)" ] ; then echo "Error: command not found 'android'" >&2 ; exit 1 ; fi
	@echo "- adb"
	@if [ -z "$(ADB)" ] ; then echo "Error: command not found 'adb'" >&2 ; exit 1 ; fi
	@echo "- ant"
	@if [ -z "$(ANT)" ] ; then echo "Error: command not found 'ant'" >&2 ; exit 1 ; fi
	@echo "- lint"
	@if [ -z "$(LINT)" ] ; then echo "Error: command not found 'lint'" >&2 ; exit 1 ; fi
	@echo "- javadoc"
	@if [ -z "$(JAVADOC)" ] ; then echo "Warning: command not found 'javadoc'" >&2 ; fi
	@echo "- doc"
	@if [ ! -d "$(ANDROID_SDK)/docs/reference" ] ; then echo "Warning: no documentation found" >&2 ; fi

update: all
	@echo "==> Git"
	@echo "- update"
	@if [ -f ".gitmodules" ] ; then git submodule --quiet update --init > /dev/null || exit 1 ; fi
	@echo "==> Libraries"
	@echo "- libs:actionbarsherlock"
	@$(ANDROID) $(ANDROID_OPTS) update lib-project --target "$(TARGET)" --path libs/actionbarsherlock/actionbarsherlock > /dev/null || exit 1
	@echo "- libs:crouton"
	@$(ANDROID) $(ANDROID_OPTS) update lib-project --target "$(TARGET)" --path libs/crouton/library > /dev/null || exit 1
	@echo "- libs:showcase"
	@$(ANDROID) $(ANDROID_OPTS) update lib-project --target "$(TARGET)" --path libs/showcase/library > /dev/null || exit 1
	@$(foreach p, $(shell find . -type f -name "AndroidManifest.xml"), mkdir -p $(shell dirname $p 2>/dev/null)/libs ;)
	@echo "- libs/*.jar"
	@$(foreach p, $(shell find . -type d -name "libs"), cp libs/*.jar $p/ 2>/dev/null ;)
	@echo "==> Projects"
	@echo "- $(PACKAGE)"
	@$(ANDROID) $(ANDROID_OPTS) update project --name $(PACKAGE) --target "$(TARGET)" --path . > /dev/null || exit 1

check: all
	@echo "==> Check"
	@if [ ! -d "bin" ] ; then echo "Error: no build" >&2 ; exit 1; fi
	@$(LINT) $(LINT_OPTS) . > /dev/null || exit 1

debug: update
	@echo "==> Build"
	@echo "- $(ANT_PROPERTIES)"
	@if [ -n "$(ANT_PROPERTIES_DEBUG)" ] ; then cp $(ANT_PROPERTIES_DEBUG) $(ANT_PROPERTIES) > /dev/null || exit 1 ; fi
	@echo "- ant debug"
	@$(ANT) $(ANT_OPTS) debug > /dev/null || (echo "E: see $(ANT_LOG) for details" >&2 ; exit 1)
	@echo "==> Sign"
	@if [ -n "$(SIGN)" ] ; then echo "- $(SIGN)" ; fi
	@echo "==> Debug"
	@cp $(APK_DEBUG) $(APK) > /dev/null || exit 1
	@echo "==> $(APK)"

release: update
	@echo "==> Build"
	@echo "- $(ANT_PROPERTIES)"
	@if [ -z "$(ANT_PROPERTIES_RELEASE)" ] ; then \
		echo "E: no $(ANT_RELEASE)" >&2 ; \
		echo "   key.store=" >&2 ; \
		echo "   key.alias=" >&2 ; \
		echo "   key.store.password=" >&2 ; \
		echo "   key.alias.password=" >&2 ; \
		exit 1 ; fi
	@cp $(ANT_PROPERTIES_RELEASE) $(ANT_PROPERTIES) > /dev/null || exit 1
	@echo "- ant debug"
	@$(ANT) $(ANT_OPTS) release > /dev/null || (echo "E: see $(ANT_LOG) for details" >&2 ; exit 1)
	@echo "==> Sign"
	@if [ -n "$(SIGN)" ] ; then echo "- $(SIGN)" ; fi
	@echo "==> Release"
	@cp $(APK_RELEASE) $(APK) > /dev/null || exit 1
	@echo "==> $(APK)"

doc: all
	@echo "==> Doc"
	@if [ -z "$(JAVADOC)" ] ; then echo "Error: command not found 'javadoc'" >&2 ; exit 1 ; fi
	@if [ ! -d "$(ANDROID_SDK)/docs/reference" ] ; then echo "Error: no documentation found" >&2 ; exit 1 ; fi
	@if [ ! -d "gen" ] ; then echo "Error: no build" >&2 ; exit 1; fi
	@$(JAVADOC) $(JAVADOC_OPTS) \
		-linkoffline http://d.android.com/reference $(ANDROID_SDK)/docs/reference \
		-classpath \
			$(shell echo $(shell find $(ANDROID_SDK)/add-ons/addon-google_apis-google-$(LATEST_API) -type f -name "*.jar" 2>/dev/null | tr "[:space:]" ":")\
				     $(shell find libs -maxdepth 1 -type f -name "*.jar" 2>/dev/null | tr "[:space:]" ":")\
				     $(ANDROID_SDK)/platforms/android-$(LATEST_API)/android.jar:\
				     $(ANDROID_SDK)/platforms/android-$(LATEST_API)/data/res:\
				     $(ANDROID_SDK)/tools/support/annotations.jar \
				| tr -d "[:space:]") \
		-sourcepath \
			$(shell echo libs/actionbarsherlock/actionbarsherlock/src:libs/actionbarsherlock/actionbarsherlock/gen:\
				     libs/crouton/library/src:libs/crouton/library/gen:\
				     libs/showcase/library/src:libs/showcase/library/gen:\
				     src \
				| tr -d "[:space:]") \
		$(PACKAGE)

clean: update
	@echo "==> Clean"
	@echo "- ant clean"
	@$(ANT) -quiet clean > /dev/null || exit 1
	@if [ -d "$(DOC)" ] ; then echo "- $(DOC)" ; fi
	@rm -rf $(DOC) > /dev/null || exit 1
	@if [ -d "$(OUT)" ] ; then echo "- $(OUT)" ; fi
	@rm -rf $(OUT) > /dev/null || exit 1
	@if [ -f "$(ANT_LOG)" ] ; then echo "- $(ANT_LOG)" ; fi
	@rm -f $(ANT_LOG) > /dev/null || exit 1
	@if [ -f "$(APK)" ] ; then echo "- $(APK)" ; fi
	@rm -f $(APK) > /dev/null || exit 1

install: all
	@echo "==> Install"
	@if [ -z "$(DEVICE)" ] ; then echo "Error: no device" >&2 ; exit 1 ; fi
	@echo "- $(DEVICE)"
	@if [ ! -f "$(APK)" ] ; then echo "Error: no apk" >&2 ; exit 1 ; fi
	@echo "- $(APK)"
	@$(ADB) install -r $(APK) || exit 1

run: all
	@echo "==> Run"
	@if [ -z "$(DEVICE)" ] ; then echo "Error: no device" >&2 ; exit 1 ; fi
	@echo "- $(DEVICE)"
	@if [ -z "$(ACTIVITY)" ] ; then echo "Error: no activity" >&2 ; exit 1 ; fi
	@echo "- $(PACKAGE)/$(ACTIVITY)"
	@$(ADB) shell am start -n $(PACKAGE)/$(ACTIVITY) > /dev/null || exit 1

uninstall: all
	@echo "==> Uninstall"
	@if [ -z "$(DEVICE)" ] ; then echo "Error: no device" >&2 ; exit 1 ; fi
	@echo "- $(DEVICE)"
	@echo "- $(PACKAGE)"
	@$(ADB) shell pm uninstall -k $(PACKAGE) || exit 1

log: all
	@echo "==> Log"
	@if [ -z "$(DEVICE)" ] ; then echo "Error: no device" >&2 ; exit 1 ; fi
	@echo "- $(DEVICE)"
	@$(ADB) logcat -s $(PACKAGE) | egrep '^[A-Z]'

help:
	@echo "update check debug release clean install run uninstall log"

.PHONY: all update check debug release clean install run uninstall log help
