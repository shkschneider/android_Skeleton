NAME = android
ANDROID = android # --silent
TARGET = $(shell $(ANDROID) list targets -c | tail -1)
ANT = ant # -quiet
CP = cp
RM = rm -f

all:
	@echo "prepare debug release clean distlcean"

prepare:
	@$(ANDROID) update project --name $(NAME) --target "$(TARGET)" --path $(NAME)

debug: prepare
	@$(ANT) -f $(NAME)/build.xml debug
	@$(CP) $(NAME)/bin/$(NAME)-debug.apk $(NAME).apk

release: prepare
	@$(ANT) -f $(NAME)/build.xml release
	@$(CP) $(NAME)/bin/$(NAME)-release.apk $(NAME).apk

clean: prepare
	@$(ANT) -f $(NAME)/build.xml clean

distclean: clean
	@$(RM) $(NAME).apk

.PHONE: all prepare debug release clean distclean
