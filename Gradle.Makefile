NAME = myapplication
ANDROID = android # --silent
TARGET = $(shell $(ANDROID) list targets -c | tail -1)
GRADLE = ./gradlew
CP = cp
RM = rm -f

all:
	@echo "apkDebug apkRelease jarDebug jarRelease clean distClean"

debug:
	@$(GRADLE) assembleDebug
	@$(CP) $(NAME)/build/outputs/apk/$(NAME)-debug.apk $(NAME).apk

release:
	@$(GRADLE) assembleRelease
	@$(CP) $(NAME)/build/outputs/apk/$(NAME)-release.apk $(NAME).apk

clean:
	@$(GRADLE) clean

distclean: clean
	@$(RM) $(NAME).apk

.PHONE: all debug release clean distclean
