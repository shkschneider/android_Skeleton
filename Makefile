NAME = $(shell basename $(shell readlink -f .))
PACKAGE = $(shell cat AndroidManifest.xml 2>/dev/null | tr "[:space:]" "\n" | grep package= | cut -d'"' -f2)

ANDROID = $(shell which android)
ADB = $(shell which adb)
ANT = $(shell which ant)
LINT = $(shell which lint)

TARGET = $(shell android list targets -c | tail -1)
DEVICE = $(shell $(ADB) devices -l | egrep '^[0-9A-F]' | head -1 | cut -c1-17)

APK = $(NAME).apk
APK_DEBUG = bin/$(NAME)-debug.apk
APK_RELEASE = bin/$(NAME)-release.apk

RM = rm -rf

all:
	@echo "==> All"
	@echo "- $(NAME)"
	@if [ -n "$(PACKAGE)" ] ; then echo "- $(PACKAGE)" ; fi
	@if [ -n "$(DEVICE)" ] ; then echo "- $(DEVICE)" ; fi

prepare:
	@echo "==> Check"

	@if [ -z "$(TARGET)" ] ; then echo "Error: bad target '$(TARGET)'" ; exit 1 ; fi
	@if [ -z "$(PACKAGE)" ] ; then echo "Error: no package" ; exit 1 ; fi
	@if [ -z "$(ANDROID)" ] ; then echo "Error: command not found 'android'" ; exit 1 ; fi
	@if [ -z "$(ADB)" ] ; then echo "Error: command not found 'adb'" ; exit 1 ; fi
	@if [ -z "$(ANT)" ] ; then echo "Error: command not found 'ant'" ; exit 1 ; fi
	@if [ -z "$(LINT)" ] ; then echo "Error: command not found 'lint'" ; exit 1 ; fi

	@echo "==> Libs"

	@if [ -f ".gitmodules" ] ; then git submodule update --init ; fi

	@echo "- actionbarsherlock"
	@$(ANDROID) --silent update lib-project --target "$(TARGET)" --path libs/actionbarsherlock/actionbarsherlock

	@echo "- appmsg"
	@$(ANDROID) --silent update lib-project --target "$(TARGET)" --path libs/appmsg/library

	@echo "- collapsiblesearchmenu"
	@$(ANDROID) --silent update lib-project --target "$(TARGET)" --path libs/collapsiblesearchmenu/library

	@echo "- crouton"
	@$(ANDROID) --silent update lib-project --target "$(TARGET)" --path libs/crouton/library

	@echo "- googleplayservice"
	@$(ANDROID) --silent update lib-project --target "$(TARGET)" --path libs/googleplayservice

	@echo "- numberpicker"
	@$(ANDROID) --silent update lib-project --target "$(TARGET)" --path libs/numberpicker/lib

	@echo "- pulltorefresh"
	@$(ANDROID) --silent update lib-project --target "$(TARGET)" --path libs/pulltorefresh

	@echo "- sidenavigation"
	@$(ANDROID) --silent update lib-project --target "$(TARGET)" --path libs/sidenavigation/library

	@echo "- ."
	@$(ANDROID) --silent update project --name $(NAME) --target "$(TARGET)" --path .

inspect: prepare
	@echo "==> Inspect"

	@$(LINT) --quiet -w -Xlint:deprecation .

debug: prepare
	@$(ANT) -quiet debug || exit 1

	@echo
	@echo "==> Debug"
	@cp $(APK_DEBUG) $(APK) || exit 1
	@echo "==> $(APK)"

release: prepare
	@$(ANT) -quiet release || exit 1

	@echo
	@echo "==> Release"
	@cp $(APK_RELEASE) $(APK) || exit 1
	@echo "==> $(APK)"

install: prepare
	@echo "==> Install"

	@if [ -z "$(DEVICE)" ] ; then echo "Error: no device" ; exit 1 ; fi
	@if [ ! -f "$(APK)" ] ; then echo "Error: no apk '$(APK)'" ; exit 1 ; fi

	@$(ADB) install -r $(APK) || exit 1

uninstall: prepare
	@echo "==> Uninstall"

	@if [ -z "$(DEVICE)" ] ; then echo "Error: no device" ; exit 1 ; fi
	@if [ -z "$(PACKAGE)" ] ; then echo "Error: no package" ; exit 1 ; fi

	@$(ADB) shell pm uninstall -k '$(PACKAGE)' || exit 1

clean: prepare
	@echo "==> Clean"
	@$(ANT) -quiet clean || exit 1
	@$(RM) $(APK)

.PHONY: all prepare inspect debug release clean
