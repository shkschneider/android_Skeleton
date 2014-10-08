PACKAGE_NAME=$(shell grep applicationId app/build.gradle | rev | cut -d' ' -f1 | rev | tr -d \')
VERSION_NAME=$(shell grep versionName app/build.gradle | rev | cut -d' ' -f1 | rev | tr -d \')
VERSION_CODE=$(shell grep versionCode app/build.gradle | rev | cut -d' ' -f1 | rev | tr -d \')
GRADLE = bash gradlew
CP = cp
RM = rm -f

all:
	@echo "$(PACKAGE_NAME) v$(VERSION_NAME) r$(VERSION_CODE)"
	@echo
	@echo "debug release clean distclean"

debug:
	@echo "$(PACKAGE_NAME)-$(VERSION_NAME)-$(VERSION_CODE).apk"
	@echo
	@$(GRADLE) assembleDebug installDebug
	@$(CP) $(NAME)/build/outputs/apk/$(NAME)-debug.apk "$(PACKAGE_NAME)-debug-$(VERSION_NAME)-$(VERSION_CODE).apk"

release:
	@echo "$(PACKAGE_NAME)-$(VERSION_NAME)-$(VERSION_CODE).apk"
	@echo
	@$(GRADLE) assembleRelease installDebug
	@$(CP) $(NAME)/build/outputs/apk/$(NAME)-release.apk "$(PACKAGE_NAME)-release-$(VERSION_NAME)-$(VERSION_CODE).apk"

clean:
	@$(GRADLE) clean

distclean: clean
	@$(RM) *.apk

.PHONE: all debug release clean distclean
