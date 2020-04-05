package me.wand555.Challenge.Config;

import org.bukkit.configuration.file.FileConfiguration;

import me.wand555.Challenge.Challenge.Challenge;

public abstract class LanguageMessages {
	
	protected final Challenge PLUGIN = Challenge.getPlugin(Challenge.class);
	protected final FileConfiguration CONFIG = PLUGIN.getConfig();
	
	public String teleportMsg;
	public String alreadyInChallenge;
	public String notInChallenge;
	public String deletedChallengeWorlds;
	public String resetWarning;
	public String noChallengeToReset;
	public String timerAlreadyStarted;
	public String noPauseBecauseNotRunning;
	public String noSettingsHasToBePaused;
	public String setHP;
	public String setHPOutOfRange;
	public String notANumber;
	public String playerNotOnline;
	public String registeredPosition;
	
	public String challengeOptionSyntax;
	public String timerOptionSyntax;
	public String hpOptionSyntax;
	public String positionSyntax;
	public String bpSyntax;
	public String settingSyntax;
	
	public String onlyForPlayers;
	
	protected abstract void loadMessagesWithLanguage();
	
	public static void prepareDefaultLangs() {
		
	}
}
