package me.wand555.Challenge.Config.Language;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.wand555.Challenge.Challenge.Challenge;

public class LanguageMessages extends ConfigUtil {
	
	protected final static Challenge PLUGIN = Challenge.getPlugin(Challenge.class);
	protected final FileConfiguration CONFIG = PLUGIN.getConfig();
	
	
	public static String teleportMsg;
	public static String alreadyInChallenge;
	public static String notInChallenge;
	public static String deletedChallengeWorlds;
	public static String resetWarning;
	public static String noChallengeToReset;
	public static String timerAlreadyStarted;
	public static String noPauseBecauseNotRunning;
	public static String noSettingsHasToBePaused;
	public static String setHP;
	public static String setHPOutOfRange;
	public static String notANumber;
	public static String playerNotOnline;
	public static String registeredPosition;
	public static String returnPosition;
	
	public static String endChallengeReset;
	public static String endChallengeComplete;
	public static String endChallengeNaturalDeath;
	public static String endChallengeNoDamage;
	public static String endChallengeNoPlace;
	public static String endChallengeNoBreak;
	public static String endChallengeNoCrafting;
	public static String endChallengeNoSneaking;
	
	public static String timerMessageStart;
	public static String timerMessagePause;
	public static String timerMessageFinished;
	
	public static String challengeOptionSyntax;
	public static String timerOptionSyntax;
	public static String hpOptionSyntax;
	public static String positionSyntax;
	public static String bpSyntax;
	public static String settingSyntax;
	
	public static String guiDeathName;
	public static String guiFortressSpawnName;
	public static String guiNoDamageName;
	public static String guiNoRegName;
	public static String guiNoRegHardName;
	public static String guiCustomHealthName;
	public static String guiSharedHealthName;
	public static String guiNoPlacingName;
	public static String guiNoBreakingName;
	public static String guiNoCraftingName;
	public static String guiNoSneakingName;
	public static String guiRandomBlockDropsName;
	public static String guiRandomMobDropsName;
	public static String guiRandomCraftingName;
	
	public static ArrayList<String> guiDeathLore;
	public static ArrayList<String> guiFortressSpawnLore;
	public static ArrayList<String> guiNoDamageLore;
	public static ArrayList<String> guiNoRegLore;
	public static ArrayList<String> guiNoRegHardLore;
	public static ArrayList<String> guiCustomHealthLore;
	public static ArrayList<String> guiSharedHealthLore;
	public static ArrayList<String> guiNoPlacingLore;
	public static ArrayList<String> guiNoBreakingLore;
	public static ArrayList<String> guiNoCraftingLore;
	public static ArrayList<String> guiNoSneakingLore;
	public static ArrayList<String> guiRandomBlockDropsLore;
	public static ArrayList<String> guiRandomMobDropsLore;
	public static ArrayList<String> guiRandomCraftingLore;
	
	public static String onlyForPlayers;
	
	public static void loadLang(Language lang) {	
		checkLangOrdner(lang.getAbbreviation());
		File file =  new File(PLUGIN.getDataFolder()+""+File.separatorChar+"Language", 
				 lang.getAbbreviation()+".yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		teleportMsg = format(cfg.getString("teleportMsg"));
		alreadyInChallenge = format(cfg.getString("alreadyInChallenge"));
		notInChallenge = format(cfg.getString("notInChallenge"));
		deletedChallengeWorlds = format(cfg.getString("deletedChallengeWorlds"));
		resetWarning = format(cfg.getString("resetWarning"));
		noChallengeToReset = format(cfg.getString("noChallengeToReset"));
		timerAlreadyStarted = format(cfg.getString("timerAlreadyStarted"));
		noPauseBecauseNotRunning = format(cfg.getString("noPauseBecauseNotRunning"));
		noSettingsHasToBePaused = format(cfg.getString("noSettingsHasToBePaused"));
		setHP = format(cfg.getString("setHP"));
		setHPOutOfRange = format(cfg.getString("setHPOutOfRange"));
		notANumber = format(cfg.getString("notANumber"));
		playerNotOnline = format(cfg.getString("playerNotOnline"));
		registeredPosition = format(cfg.getString("registeredPosition"));
		returnPosition = format(cfg.getString("returnPosition"));
		
		endChallengeReset = format(cfg.getString("endChallengeReset"));
		endChallengeComplete = format(cfg.getString("endChallengeComplete"));
		endChallengeNaturalDeath = format(cfg.getString("endChallengeNaturalDeath"));
		endChallengeNoDamage = format(cfg.getString("endChallengeNoDamage"));
		endChallengeNoPlace = format(cfg.getString("endChallengeNoPlace"));
		endChallengeNoBreak = format(cfg.getString("endChallengeNoBreak"));
		endChallengeNoCrafting = format(cfg.getString("endChallengeNoCrafting"));
		endChallengeNoSneaking = format(cfg.getString("endChallengeNoSneaking"));
		
		timerMessageStart = formatWithoutPrefix(cfg.getString("timerMessageStart"));
		timerMessagePause = formatWithoutPrefix(cfg.getString("timerMessagePause"));
		timerMessageFinished = formatWithoutPrefix(cfg.getString("timerMessageFinished"));
		
		challengeOptionSyntax = format(cfg.getString("challengeOptionSyntax"));
		timerOptionSyntax = format(cfg.getString("timerOptionSyntax"));
		hpOptionSyntax = format(cfg.getString("hpOptionSyntax"));
		positionSyntax = format(cfg.getString("positionSyntax"));
		bpSyntax = format(cfg.getString("bpSyntax"));
		settingSyntax = format(cfg.getString("settingSyntax"));
		
		guiDeathName = formatWithoutPrefix(cfg.getString("guiDeathName"));
		guiFortressSpawnName = formatWithoutPrefix(cfg.getString("guiFortressSpawnName"));
		guiNoDamageName = formatWithoutPrefix(cfg.getString("guiNoDamageName"));
		guiNoRegName = formatWithoutPrefix(cfg.getString("guiNoRegName"));
		guiNoRegHardName = formatWithoutPrefix(cfg.getString("guiNoRegHardName"));
		guiCustomHealthName = formatWithoutPrefix(cfg.getString("guiCustomHealthName"));
		guiSharedHealthName = formatWithoutPrefix(cfg.getString("guiSharedHealthName"));
		guiNoPlacingName = formatWithoutPrefix(cfg.getString("guiNoPlacingName"));
		guiNoBreakingName = formatWithoutPrefix(cfg.getString("guiNoBreakingName"));
		guiNoCraftingName = formatWithoutPrefix(cfg.getString("guiNoCraftingName"));
		guiNoSneakingName = formatWithoutPrefix(cfg.getString("guiNoSneakingName"));
		guiRandomBlockDropsName = formatWithoutPrefix(cfg.getString("guiRandomBlockDropsName"));
		guiRandomMobDropsName = formatWithoutPrefix(cfg.getString("guiRandomMobDropsName"));
		guiRandomCraftingName = formatWithoutPrefix(cfg.getString("guiRandomCraftingName"));
	
		//guiDeathLore = formatWithoutPrefixList(Arrays.asList(cfg.getString("guiDeathLore").split("|")));
		
	}
	
	public static String format(String msg) {
		return Challenge.PREFIX + ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static String formatWithoutPrefix(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static ArrayList<String> formatWithoutPrefixList(List<String> list) {
		return list.stream().map(s -> formatWithoutPrefix(s)).collect(Collectors.toCollection(ArrayList::new));
	}
}
