# Makefile

APP		= demo
APP_ID		= $(shell grep 'applicationId' $(APP)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev | tr -d \' | tr -d \")
SKELETON	= library
SKELETON_ID	= me.shkschneider.skeleton
MIN_SDK		= $(shell grep 'minSdkVersion' $(APP)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev)
TARGET_SDK	= $(shell grep 'targetSdkVersion' $(APP)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev)
VERSION_NAME	= $(shell grep 'skeleton' build.gradle | head -1 | rev | cut -d' ' -f1 | rev | tr -d \' | tr -d \" | tr -d \,)
VERSION_CODE	= $(shell cat $(APP)/version.properties | sed -n '2p' | cut -d'=' -f2)
VERSION		= "v$(VERSION_NAME)r$(VERSION_CODE)"
GRADLE		= bash gradlew

all:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@echo "debug release lint clean distclean"

debug:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@$(GRADLE) assembleDebug #installDebug
	@echo "$(SKELETON_ID)-*_$(VERSION)-debug.aar"
	@echo "$(APP_ID)-*_$(VERSION)-debug.apk"

release:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@$(GRADLE) assembleRelease #installRelease
	@echo "$(SKELETON_ID)-*_$(VERSION)-release.aar"
	@echo "$(APP_ID)-*_$(VERSION)-release.apk"

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
