NAME = myapplication
ANDROID = android # --silent
TARGET = $(shell $(ANDROID) list targets -c | tail -1)
GRADLE = ./gradlew
CP = cp
RM = rm -f

all:
	@echo "apkDebug apkRelease jarDebug jarRelease clean distClean"

apkDebug:
	@$(GRADLE) assembleDebug
	@$(CP) $(NAME)/build/apk/$(NAME)-debug-unaligned.apk $(NAME).apk

apkRelease:
	@$(GRADLE) assembleRelease
	@$(CP) $(NAME)/build/apk/$(NAME)-release.apk $(NAME).apk

jarDebug:
	@$(GRADLE) jarDebug
	@$(CP) $(NAME)/build/libs/$(NAME).jar $(NAME).jar

jarRelease:
	@$(GRADLE) jarRelease
	@$(CP) $(NAME)/build/libs/$(NAME).jar $(NAME).jar

clean:
	@$(GRADLE) clean

distClean: clean
	@$(RM) $(NAME).apk
	@$(RM) $(NAME).jar

.PHONE: all apkDebug apkRelease jarDebug jarRelease clean distClean
