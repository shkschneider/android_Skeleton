# Makefile

APP		= demo
APP_ID		= $(shell grep 'applicationId' $(APP)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev | tr -d \' | tr -d \")
SKELETON	= library
SKELETON_ID	= me.shkschneider.skeleton
MIN_SDK		= $(shell grep 'minSdkVersion' $(APP)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev)
TARGET_SDK	= $(shell grep 'targetSdkVersion' $(APP)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev)
VERSION_NAME	= $(shell grep 'versionName' $(APP)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev | tr -d \' | tr -d \")
VERSION_CODE	= $(shell grep 'versionCode' $(APP)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev | tr -d \' | tr -d \")
VERSION		= "v$(VERSION_NAME)r$(VERSION_CODE)"
GRADLE		= bash gradlew

all:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@echo "debug release lint clean distclean"

debug:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@$(GRADLE) assembleDebug #installDebug
	@cp "$(SKELETON)/build/outputs/aar/$(SKELETON)-debug.aar" "$(SKELETON_ID)-debug-$(VERSION).aar"
	@echo "$(SKELETON_ID)-debug-$(VERSION).aar"
	@cp "$(APP)/build/outputs/apk/$(APP)-universal-debug.apk" "$(APP_ID)-debug-$(VERSION).apk"
	@echo "$(APP_ID)-debug-$(VERSION).apk"

release:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@$(GRADLE) assembleRelease #installRelease
	@cp "$(SKELETON)/build/outputs/aar/$(SKELETON)-release.aar" "$(SKELETON_ID)-release-$(VERSION).aar"
	@echo "$(SKELETON_ID)-release-$(VERSION).aar"
	@cp "$(APP)/build/outputs/apk/$(APP)-universal-release.apk" "$(APP_ID)-release-$(VERSION).apk"
	@echo "$(APP_ID)-release-$(VERSION).apk"

lint:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@$(GRADLE) :demo:lint
	@echo "demo/build/outputs/lint-results-debug.html"
	@echo "demo/build/outputs/lint-results-debug.xml"

clean:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@$(GRADLE) clean

distclean:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@rm -f *.aar
	@rm -f *.apk

.PHONE: all debug release lint clean distclean

# EOF
