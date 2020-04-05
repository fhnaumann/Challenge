package me.wand555.Challenge.Config;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import me.wand555.Challenge.Challenge.Challenge;

public class LanguageMessageEnglish extends LanguageMessages {

	private final File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"Language"+File.separatorChar, 
			Language.ENGLISH.getAbbreviation()+".yml");
	
	public LanguageMessageEnglish() {
		
	}

	@Override
	protected void loadMessagesWithLanguage() {
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		super.teleportMsg = Challenge.PREFIX + ChatColor.GRAY + "Teleported.";
		super.alreadyInChallenge = Challenge.PREFIX + ChatColor.GRAY + "You're already in the challenge.";
		super.notInChallenge = Challenge.PREFIX + ChatColor.GRAY + "You're not in a challenge.";
		super.deletedChallengeWorlds = Challenge.PREFIX + ChatColor.GRAY + "Deleted challenge worlds.";
		super.resetWarning = Challenge.PREFIX + ChatColor.GRAY + "Reload/Restart the server and type /challenge join to join a new challenge.";
		super.noChallengeToReset = Challenge.PREFIX + ChatColor.GRAY + "You have to be in the challenge to reset it.";
		super.timerAlreadyStarted = Challenge.PREFIX + ChatColor.GRAY + "Timer is started. Use /timer pause to pause or resume the timer.";
		super.noPauseBecauseNotRunning = Challenge.PREFIX + ChatColor.GRAY + "Cannot pause. Challenge is not running.";
		super.noSettingsHasToBePaused = Challenge.PREFIX + ChatColor.GRAY + "Timer has to be paused. /timer pause";
		super.setHP = Challenge.PREFIX + ChatColor.GRAY + "Set HP.";
		super.setHPOutOfRange = Challenge.PREFIX + ChatColor.GRAY + "Amount has to be 0 <= amount < MAX_HEALTH.";
		super.notANumber = Challenge.PREFIX + ChatColor.GRAY + "'" + ChatColor.GREEN + "[NUMBER]" + ChatColor.GRAY + "' is not a number.";
		super.playerNotOnline = Challenge.PREFIX + ChatColor.GRAY + "The player '" + ChatColor.GREEN + "[PLAYER]" + ChatColor.GRAY + "' is not online.";
		super.registeredPosition = Challenge.PREFIX + ChatColor.GRAY + "Registered position " + ChatColor.GREEN + "[POS]";
	}
	
}
