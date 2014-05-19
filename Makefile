# Makefile

DIST = dist
GRADLE = bash gradlew

APP_NAME = app
APP_VERSION = $(shell egrep 'versionName' $(APP_NAME)/build.gradle | cut -d'"' -f2)

SKELETON_NAME = skeleton
SKELETON_VERSION = $(shell egrep 'versionName' $(APP_NAME)/build.gradle | cut -d'"' -f2)

all:
	@echo
	@echo "[help]"
	@echo
	@echo "debug     -- compiles debug app apk and skeleton library"
	@echo "release   -- compiles release app apk and skeleton library"
	@echo "clean     -- gradle clean"
	@echo "distclean -- gradle clean and generated files"

debug:
	@echo
	@echo "[debug]"
	@echo
	@echo "$(APP_NAME): $(APP_VERSION)"
	@echo "$(SKELETON_NAME): $(SKELETON_VERSION)"
	@echo
	@$(GRADLE) clean assembleDebug || exit 1
	@echo
	@mkdir -p $(DIST) || exit 1
	@cp -p $(APP_NAME)/build/apk/$(APP_NAME)-debug.apk $(DIST)/$(APP_NAME)-debug-$(APP_VERSION).apk || exit 1
	@echo "[APK] $(DIST)/$(APP_NAME)-debug-$(APP_VERSION).apk"
	@cp -p $(SKELETON_NAME)/build/libs/$(SKELETON_NAME).aar $(DIST)/$(SKELETON_NAME)-debug-$(SKELETON_VERSION).aar || exit 1
	@echo "[AAR] $(DIST)/$(SKELETON_NAME)-debug-$(SKELETON_VERSION).aar"

release:
	@echo
	@echo "[release]"
	@echo
	@echo "$(APP_NAME): $(APP_VERSION)"
	@echo "$(SKELETON_NAME): $(SKELETON_VERSION)"
	@echo
	@$(GRADLE) clean assembleRelease || exit 1
	@echo
	@mkdir -p dist || exit 1
	@cp -p $(APP_NAME)/build/apk/$(APP_NAME)-release.apk $(DIST)/$(APP_NAME)-release-$(APP_VERSION).apk || exit 1
	@echo "[APK] $(DIST)/app-release-$(APP_VERSION).apk"
	@cp -p $(SKELETON_NAME)/build/libs/$(SKELETON_NAME).aar $(DIST)/$(SKELETON_NAME)-release-$(SKELETON_VERSION).aar || exit 1
	@echo "[AAR] $(DIST)/$(SKELETON_NAME)-release-$(SKELETON_VERSION).aar"

clean:
	@echo
	@echo "[clean]"
	@echo
	@$(GRADLE) clean

distclean: clean
	@echo
	@echo "[distclean]"
	@echo
	@echo "$(DIST)/*.apk"
	@rm -rf $(DIST)/*.apk
	@echo "$(DIST)/*.aar"
	@rm -rf $(DIST)/*.aar

.PHONY: bump debug release clean distclean

# EOF
