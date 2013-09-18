MANIFEST = $(shell find . -type f -name "AndroidManifest.xml" 2>/dev/null | sort | head -1)
DIR = $(shell dirname $(shell readlink -f "$(MANIFEST)" 2>/dev/null) 2>/dev/null)
ACTIVITY = $(shell cat "$(MANIFEST)" 2>/dev/null | tr "[:space:]" "\n" | grep 'android:name=' | grep Activity | head -1 | cut -d'"' -f2)

SDK = ${ANDROID_HOME}
API = $(shell cat $(DIR)/AndroidManifest.xml 2>/dev/null | tr "[:space:]" "\n" | grep minSdkVersion= | cut -d'"' -f2)
PACKAGE = $(shell cat $(DIR)/AndroidManifest.xml 2>/dev/null | tr "[:space:]" "\n" | grep package= | cut -d'"' -f2)
SUPPORT = $(shell find $(SDK)/extras/android/support/v4 -maxdepth 1 -type f -name "android-support-v4.jar" 2>/dev/null | head -1)
VERSION_NAME = $(shell cat $(DIR)/AndroidManifest.xml 2>/dev/null | tr "[:space:]" "\n" | grep versionName= | cut -d'"' -f2)
VERSION_CODE = $(shell cat $(DIR)/AndroidManifest.xml 2>/dev/null | tr "[:space:]" "\n" | grep versionCode= | cut -d'"' -f2)
SIGN = $(shell basename $(shell find $(DIR) -maxdepth 1 -type f -name ant.properties 2>/dev/null) 2>/dev/null)

ANDROID = $(shell which android 2>/dev/null)
ANDROID_OPTS = --silent
ADB = $(shell which adb 2>/dev/null)
ADB_OPTS =
ANT = $(shell which ant 2>/dev/null)
ANT_PROPERTIES = "ant.properties"
ANT_DEBUG = "ant.debug"
ANT_RELEASE = "ant.release"
ANT_PROPERTIES_DEBUG = $(shell find . -maxdepth 1 -name $(ANT_DEBUG) 2>/dev/null)
ANT_PROPERTIES_RELEASE = $(shell find . -maxdepth 1 -name $(ANT_RELEASE) 2>/dev/null)
ANT_LOG = "build.log"
ANT_OPTS = -logfile $(ANT_LOG)
LINT = $(shell which lint 2>/dev/null)
LINT_OPTS = --quiet -w -Xlint:deprecation

TARGET = $(shell $(ANDROID) list targets -c 2>/dev/null | tail -1)
DEVICE = $(shell $(ADB) devices -l 2>/dev/null | egrep '^[0-9A-F]' | head -1 | cut -c1-17)

APK = $(PACKAGE).apk
APK_DEBUG = bin/$(PACKAGE)-debug.apk
APK_RELEASE = bin/$(PACKAGE)-release.apk

all:
	@if [ -z "$(DIR)" ] ; then echo "Fatal error" ; exit 1 ; fi
	@echo "==> Directory"
	@echo "- $(DIR)"
	@cd $(DIR) > /dev/null || exit 1
	@echo "==> Manifest"
	@if [ -z "$(MANIFEST)" ] ; then echo "Error: no manifest" ; exit 1 ; fi
	@echo "- $(MANIFEST)"
	@if [ -z "$(PACKAGE)" ] ; then echo "Error: no package" ; exit 1 ; fi
	@echo "- $(PACKAGE)"
	@if [ -z "$(API)" ] ; then echo "Error: no api" ; exit 1 ; fi
	@echo "- API-$(API)"
	@if [ -z "$(VERSION_NAME)" -o -z "$(VERSION_CODE)" ] ; then echo "Error: no version" ; exit 1 ; fi
	@echo "- v$(VERSION_NAME) b$(VERSION_CODE)"
	@echo "==> SDK"
	@if [ -z "$(SDK)" ] ; then echo "Error: no sdk" ; exit 1 ; fi
	@echo "- $(SDK)"
	@if [ -z "$(TARGET)" ] ; then echo "Error: no target" ; exit 1 ; fi
	@echo "- $(TARGET)"
	@if [ -z "$(SUPPORT)" ] ; then echo "Error: no support library" ; exit 1 ; fi
	@echo "- sdk:$(shell echo $(SUPPORT) | sed -r 's#$(SDK)/##')"
	@echo "==> Device"
	@if [ -n "$(DEVICE)" ] ; then echo "- $(DEVICE)" ; fi
	@echo "==> Binaries"
	@echo "- android"
	@if [ -z "$(ANDROID)" ] ; then echo "Error: command not found 'android'" ; exit 1 ; fi
	@echo "- adb"
	@if [ -z "$(ADB)" ] ; then echo "Error: command not found 'adb'" ; exit 1 ; fi
	@echo "- ant"
	@if [ -z "$(ANT)" ] ; then echo "Error: command not found 'ant'" ; exit 1 ; fi
	@echo "- lint"
	@if [ -z "$(LINT)" ] ; then echo "Error: command not found 'lint'" ; exit 1 ; fi

update: all
	@echo "==> Git"
	@echo "- update"
	@if [ -f ".gitmodules" ] ; then git submodule update --init > /dev/null || exit 1 ; fi
	@echo "==> Libraries"
	@$(foreach p, $(shell find . -type f -name "AndroidManifest.xml"), mkdir -p $(shell dirname $p 2>/dev/null)/libs ;)
	@echo "- libs"
	@$(foreach p, $(shell find . -type d -name "libs"), cp libs/*.jar $p/ 2>/dev/null ;)
	@echo "- libs:actionbarsherlock"
	@$(ANDROID) $(ANDROID_OPTS) update lib-project --target "$(TARGET)" --path libs/actionbarsherlock/actionbarsherlock > /dev/null || exit 1
	@echo "- libs:crouton"
	@$(ANDROID) $(ANDROID_OPTS) update lib-project --target "$(TARGET)" --path libs/crouton/library > /dev/null || exit 1
	@echo "- libs:showcase"
	@$(ANDROID) $(ANDROID_OPTS) update lib-project --target "$(TARGET)" --path libs/showcase/library > /dev/null || exit 1
	@echo "==> Projects"
	@echo "- $(PACKAGE)"
	@$(ANDROID) $(ANDROID_OPTS) update project --name $(PACKAGE) --target "$(TARGET)" --path . > /dev/null || exit 1

check: all
	@echo "==> Check"
	@if [ ! -d "bin" ] ; then echo "Error: no build" ; exit 1; fi
	@$(LINT) $(LINT_OPTS) . > /dev/null || exit 1

debug: update
	@echo "==> Build"
	@echo "- $(ANT_PROPERTIES)"
	@if [ -n "$(ANT_PROPERTIES_DEBUG)" ] ; then cp $(ANT_PROPERTIES_DEBUG) $(ANT_PROPERTIES) > /dev/null || exit 1 ; fi
	@echo "- ant debug"
	@$(ANT) $(ANT_OPTS) debug > /dev/null || (echo "E: see $(ANT_LOG) for details" ; exit 1)
	@echo "==> Sign"
	@if [ -n "$(SIGN)" ] ; then echo "- $(SIGN)" ; fi
	@echo "==> Debug"
	@cp $(APK_DEBUG) $(APK) > /dev/null || exit 1
	@echo "==> $(APK)"

release: update
	@echo "==> Build"
	@echo "- $(ANT_PROPERTIES)"
	@if [ -z "$(ANT_PROPERTIES_RELEASE)" ] ; then \
		echo "E: no $(ANT_RELEASE)" ; \
		echo "   key.store=" ; \
		echo "   key.alias=" ; \
		echo "   key.store.password=" ; \
		echo "   key.alias.password=" ; \
		exit 1 ; fi
	@cp $(ANT_PROPERTIES_RELEASE) $(ANT_PROPERTIES) > /dev/null || exit 1
	@echo "- ant debug"
	@$(ANT) $(ANT_OPTS) release > /dev/null || (echo "E: see $(ANT_LOG) for details" ; exit 1)
	@echo "==> Sign"
	@if [ -n "$(SIGN)" ] ; then echo "- $(SIGN)" ; fi
	@echo "==> Release"
	@cp $(APK_RELEASE) $(APK) > /dev/null || exit 1
	@echo "==> $(APK)"

clean: all
	@echo "==> Clean"
	@$(ANT) -quiet clean > /dev/null || exit 1
	@rm -rf $(ANT_LOG) > /dev/null || exit 1
	@rm -rf bin out > /dev/null || exit 1
	@rm -f $(APK) > /dev/null || exit 1

install: all
	@echo "==> Install"
	@if [ -z "$(DEVICE)" ] ; then echo "Error: no device" ; exit 1 ; fi
	@echo "- $(DEVICE)"
	@if [ ! -f "$(APK)" ] ; then echo "Error: no apk" ; exit 1 ; fi
	@echo "- $(APK)"
	@$(ADB) install -r $(APK) || exit 1

run: all
	@echo "==> Run"
	@if [ -z "$(DEVICE)" ] ; then echo "Error: no device" ; exit 1 ; fi
	@echo "- $(DEVICE)"
	@if [ -z "$(ACTIVITY)" ] ; then echo "Error: no activity" ; exit 1 ; fi
	@echo "- $(PACKAGE)/$(ACTIVITY)"
	@$(ADB) shell am start -n $(PACKAGE)/$(ACTIVITY) > /dev/null || exit 1

uninstall: all
	@echo "==> Uninstall"
	@if [ -z "$(DEVICE)" ] ; then echo "Error: no device" ; exit 1 ; fi
	@echo "- $(DEVICE)"
	@echo "- $(PACKAGE)"
	@$(ADB) shell pm uninstall -k $(PACKAGE) || exit 1

log: all
	@echo "==> Log"
	@if [ -z "$(DEVICE)" ] ; then echo "Error: no device" ; exit 1 ; fi
	@echo "- $(DEVICE)"
	@$(ADB) logcat -s $(PACKAGE) | egrep '^[A-Z]'

help:
	@echo "update check debug release clean install run uninstall log"

.PHONY: all update check debug release clean install run uninstall log help
