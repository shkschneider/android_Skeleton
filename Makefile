# Makefile

APP		= demo
APP_ID		= $(shell grep 'applicationId' $(APP)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev | tr -d \' | tr -d \")
SKELETON	= library
SKELETON_ID	= me.shkschneider.skeleton
MIN_SDK		= $(shell grep 'minSdkVersion' $(APP)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev)
TARGET_SDK	= $(shell grep 'targetSdkVersion' $(APP)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev)
VERSION_NAME	= $(shell grep 'skeleton' build.gradle | head -1 | rev | cut -d' ' -f1 | rev | tr -d \' | tr -d \" | tr -d \,)
VERSION_CODE	= $(shell cat $(APP)/version.properties | sed -n '2p' | cut -d'=' -f2)
VERSION		= v$(VERSION_NAME)r$(VERSION_CODE)
GRADLE		= bash gradlew

all:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@echo "debug release lint clean distclean"

debug:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@$(GRADLE) assembleDebug #installDebug
	@find $(SKELETON)/build -type f -name '*$(VERSION)*.aar'
	@find $(APP)/build -type f -name '*$(VERSION)*.apk'

release:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@$(GRADLE) assembleRelease #installRelease
	@find $(SKELETON)/build -type f -name '*$(VERSION)*.aar'
	@find $(APP)/build -type f -name '*$(VERSION)*.apk'

doc:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@$(GRADLE) :$(SKELETON):doc
	@find $(SKELETON)/build -maxdepth 3 -type f -name 'index.html'

lint:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@$(GRADLE) :$(APP):lint
	@find $(APP)/build -type f -name 'lint-results*'

clean:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@$(GRADLE) clean

distclean:
	@echo "[ $(APP_ID) $(VERSION) ]"
	@rm -f *.aar
	@rm -f *.apk

.PHONE: all debug release lint clean distclean

# EOF
