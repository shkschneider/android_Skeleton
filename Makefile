GRADLE = ./gradlew

all:
	@$(GRADLE) check build

check:
	@$(GRADLE) check

debug:
	@$(GRADLE) check build installDebug

release:
	@$(GRADLE) check build installRelease

clean:
	@$(GRADLE) clean

fclean:
	@$(GRADLE) clean uninstallAll

re:
	@$(GRADLE) clean check build
