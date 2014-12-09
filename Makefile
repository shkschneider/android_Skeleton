# Makefile

NAME		= app
PACKAGE_NAME	= $(shell grep 'applicationId' $(NAME)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev | tr -d \' | tr -d \")
MIN_SDK		= $(shell grep 'minSdkVersion' $(NAME)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev)
TARGET_SDK	= $(shell grep 'targetSdkVersion' $(NAME)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev)
VERSION_NAME	= $(shell grep 'versionName' $(NAME)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev | tr -d \' | tr -d \")
VERSION_CODE	= $(shell grep 'versionCode' $(NAME)/build.gradle | head -1 | rev | cut -d' ' -f1 | rev | tr -d \' | tr -d \")
VERSION		= "v$(VERSION_NAME)r$(VERSION_CODE)"
GRADLE		= bash gradlew

all:
	@echo "[ $(PACKAGE_NAME) $(VERSION) ]"
	@echo "debug release lint clean distclean"

debug:
	@echo "[ $(PACKAGE_NAME) $(VERSION) ]"
	@$(GRADLE) assembleDebug #installDebug
	@cp "$(NAME)/build/outputs/apk/$(NAME)-debug.apk" "$(PACKAGE_NAME)-debug-$(VERSION).apk"
	@echo "$(PACKAGE_NAME)-debug-$(VERSION).apk"

release:
	@echo "[ $(PACKAGE_NAME) $(VERSION) ]"
	@$(GRADLE) assembleRelease #installDebug
	@cp "$(NAME)/build/outputs/apk/$(NAME)-release.apk" "$(PACKAGE_NAME)-release-$(VERSION).apk"
	@echo "$(PACKAGE_NAME)-release-$(VERSION).apk"

lint:
	@echo "[ $(PACKAGE_NAME) $(VERSION) ]"
	@$(GRADLE) :app:lint
	@echo "app/build/outputs/lint-results.html"
	@echo "app/build/outputs/lint-results.xml"

clean:
	@echo "[ $(PACKAGE_NAME) $(VERSION) ]"
	@$(GRADLE) clean

distclean:
	@echo "[ $(PACKAGE_NAME) $(VERSION) ]"
	@rm -f "*.apk"

.PHONE: all debug release lint clean distclean

# EOF
