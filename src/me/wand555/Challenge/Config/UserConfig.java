package me.wand555.Challenge.Config;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.collect.Lists;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.Config.Language.ConfigUtil;
import me.wand555.Challenge.Config.Language.Language;
import me.wand555.Challenge.Config.Language.LanguageMessages;

public class UserConfig extends ConfigUtil {

	public static final Challenge PLUGIN = Challenge.getPlugin(Challenge.class);
	
	public static void setUpDefaultConfig() {
		PLUGIN.getConfig().options().copyDefaults(true);
		PLUGIN.getConfig().addDefault("Language", "#Supported Languages:");
		PLUGIN.getConfig().addDefault("Language", "#English - " + Language.ENGLISH.getAbbreviation());
		PLUGIN.getConfig().addDefault("Language", "#German - " + Language.GERMAN.getAbbreviation());
		PLUGIN.getConfig().addDefault("Language", "en");
		PLUGIN.saveConfig();
	}
	
	public static LanguageMessages setUpLanguage() {
		
		return null;
	}
	
	public static void placeLanguages() {
		placeLanguageEn();
		placeLanguageDe();
	}
	
	private static void placeLanguageEn() {
		checkLangOrdner(Language.ENGLISH.getAbbreviation());
		File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"Language", 
				 Language.ENGLISH.getAbbreviation()+".yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		Map<String, String> msgDefaults = new LinkedHashMap<String, String>();
		msgDefaults.put("teleportMsg", "&7Teleported.");
		msgDefaults.put("alreadyInChallenge", "&7You're already in the challenge.");
		msgDefaults.put("notInChallenge", "&7You're not in a challenge.");
		msgDefaults.put("deletedChallengeWorlds", "&7Deleted challenge worlds.");
		msgDefaults.put("resetWarning", "&7Reload/Restart the server and type /challenge join to join a new challenge.");
		msgDefaults.put("noChallengeToReset", "&7You have to be in the challenge to reset it.");
		msgDefaults.put("timerAlreadyStarted", "&7Timer is started. Use /timer pause to pause or resume the timer.");
		msgDefaults.put("noPauseBecauseNotRunning", "&7Cannot pause. Challenge is not running.");
		msgDefaults.put("noSettingsHasToBePaused", "&7Timer has to be paused. /timer pause");
		msgDefaults.put("setHP", "&7Set HP.");
		msgDefaults.put("setHPOutOfRange", "&7Amount has to be 0 <= amount < MAX_HEALTH.");
		msgDefaults.put("notANumber", "&7'&a[NUMBER]&7' is not a number.");
		msgDefaults.put("playerNotOnline", "&7The player '&a[PLAYER]&7' is not online.");
		msgDefaults.put("registeredPosition", "&7Registered position &a[POS]&7.");
		msgDefaults.put("returnPosition", "&7Position: &2&l[X]&7/&2&l[Y]&7/&2&l[Z] &7'&a[POSNAME]&7' in the world &2[WORLD]&7.");
		
		msgDefaults.put("endChallengeReset", "&7Type &a/challenge reset &7to reset the challenge.");
		msgDefaults.put("endChallengeComplete", "&7The challenge has been completed in &2&l[TIME]&7!");
		msgDefaults.put("endChallengeNaturalDeath", "&2[PLAYER] &7died! The challenge ended!");
		msgDefaults.put("endChallengeNoDamage", "&2[PLAYER] &7took damage! The challenge ended!");
		msgDefaults.put("endChallengeNoPlace", "&2[PLAYER] &7placed a block! The challenge ended!");
		msgDefaults.put("endChallengeNoBreak", "&2[PLAYER] &7broke a block! The challenge ended!");
		msgDefaults.put("endChallengeNoCrafting", "&2[PLAYER] &7crafted an item! The challenge ended!");
		msgDefaults.put("endChallengeNoSneaking", "&2[PLAYER] &7sneaked! The challenge ended!");
		
		msgDefaults.put("timerMessageStart", "&7&l/timer start");
		msgDefaults.put("timerMessagePause", "&7&lPAUSED &2[TIME] &7- /timer pause");
		msgDefaults.put("timerMessageFinished", "&7&lFinal Time: &2[TIME] &7 - /challenge reset or /challenge restore");
		
		msgDefaults.put("challengeOptionSyntax", "&7Syntax: /challenge join:leave:restore:reset");
		msgDefaults.put("timerOptionSyntax", "&7Syntax: &a/timer start:pause");
		msgDefaults.put("hpOptionSyntax", "&7Syntax: &a/hp <Amount> <Player>");
		msgDefaults.put("positionSyntax", "&7Syntax: &a/pos <Name>");
		msgDefaults.put("bpSyntax", "&7Syntax: &a/bp");
		msgDefaults.put("settingSyntax", "&7Syntax: &a/settings");
		
		msgDefaults.put("guiDeathName", "&7&lDeath");
		msgDefaults.put("guiFortressSpawnName", "&7&lGuarantee Fortress Spawn");
		msgDefaults.put("guiNoDamageName", "&7&lNo Damage");
		msgDefaults.put("guiNoRegName", "&7&lNo Regeneration");
		msgDefaults.put("guiNoRegHardName", "&7&lNo Regeneration");
		msgDefaults.put("guiCustomHealthName", "&7&lCustom Health");
		msgDefaults.put("guiSharedHealthName", "&7&lShared Health");
		msgDefaults.put("guiNoPlacingName", "&7&lNo Placing");
		msgDefaults.put("guiNoBreakingName", "&7&lNo Breaking");
		msgDefaults.put("guiNoCraftingName", "&7&lNo Crafting");
		msgDefaults.put("guiNoSneakingName", "&7&lNo Sneaking");
		msgDefaults.put("guiRandomBlockDropsName", "&7&lRandomized Blockdrops");
		msgDefaults.put("guiRandomMobDropsName", "&7&lRandomized Mobdrops");
		msgDefaults.put("guiRandomCraftingName", "&7&lRandomized Crafting");
		
		msgDefaults.put("guiDeathLore", "The challenge is over|when someone dies!");
		
		msgDefaults.keySet().stream()
			.filter(key -> !cfg.isSet(key))
			.forEachOrdered(key -> cfg.set(key, msgDefaults.get(key)));
		
		saveCustomYml(cfg, file);
		msgDefaults.clear();
	}
	
	private static void placeLanguageDe() {
		checkLangOrdner(Language.GERMAN.getAbbreviation());
		File file = new File(PLUGIN.getDataFolder()+""+File.separatorChar+"Language", 
				 Language.GERMAN.getAbbreviation()+".yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		Map<String, String> msgDefaults = new LinkedHashMap<String, String>();
		msgDefaults.put("teleportMsg", "&7Teleportiert.");
		msgDefaults.put("alreadyInChallenge", "&7Du bist bereits in einer Challenge.");
		msgDefaults.put("notInChallenge", "&7Du bist in keiner Challenge.");
		msgDefaults.put("deletedChallengeWorlds", "&7Challenge Welten gelöscht.");
		msgDefaults.put("resetWarning", "&7Reloade/Restarte den Server und und joine erneut mit /challenge join für eine neue Challenge.");
		msgDefaults.put("noChallengeToReset", "&7Du musst dich in einer Challenge Welt befinden.");
		msgDefaults.put("timerAlreadyStarted", "&7Timer läuft bereits. Pausiere/Starte den Timer mit /timer pause");
		msgDefaults.put("noPauseBecauseNotRunning", "&7Challenge läuft nicht.");
		msgDefaults.put("noSettingsHasToBePaused", "&7Timer muss pausiert sein. /timer pause");
		msgDefaults.put("setHP", "&7HP gesetzt.");
		msgDefaults.put("setHPOutOfRange", "&7HP müssen zwischen 0 und der maximalen Herzen liegen.");
		msgDefaults.put("notANumber", "&7'&a[NUMBER]&7' ist keine gültige Zahl.");
		msgDefaults.put("playerNotOnline", "&7Der Spieler '&a[PLAYER]&7' ist nicht online.");
		msgDefaults.put("registeredPosition", "&a[POS]&7 hinzugefügt.");
		msgDefaults.put("returnPosition", "&7Position: &2&l[X]&7/&2&l[Y]&7/&2&l[Z] &7'&a[POSNAME]&7' in der Welt &2[WORLD]&7.");
		
		msgDefaults.put("endChallengeReset", "&7Nutze &a/challenge reset &7um die Challenge zu beenden.");
		msgDefaults.put("endChallengeComplete", "&7Die Challenge wurde in &2&l[TIME] &7gemeistert!");
		msgDefaults.put("endChallengeNaturalDeath", "&2[PLAYER] &7starb! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoDamage", "&2[PLAYER] &7nahm Schaden! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoPlace", "&2[PLAYER] &7platzierte einen Block! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoBreak", "&2[PLAYER] &7baute einen Block ab! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoCrafting", "&2[PLAYER] &7craftete ein Item! Die Challenge ist vorbei!");
		msgDefaults.put("endChallengeNoSneaking", "&2[PLAYER] &7sneakte! Die Challenge ist vorbei!");
		
		msgDefaults.put("timerMessageStart", "&7&l/timer start");
		msgDefaults.put("timerMessagePause", "&7&lPAUSIERT &2[TIME] &7- /timer pause");
		msgDefaults.put("timerMessageFinished", "&7&lFinale Zeit: &2[TIME] &7 - /challenge reset oder /challenge restore");
		
		msgDefaults.put("challengeOptionSyntax", "&7Syntax: /challenge join:leave:restore:reset");
		msgDefaults.put("timerOptionSyntax", "&7Syntax: &a/timer start:pause");
		msgDefaults.put("hpOptionSyntax", "&7Syntax: &a/hp <Anzahl> <Spieler>");
		msgDefaults.put("positionSyntax", "&7Syntax: &a/pos <Name>");
		msgDefaults.put("bpSyntax", "&7Syntax: &a/bp");
		msgDefaults.put("settingSyntax", "&7Syntax: &a/settings");
		
		msgDefaults.put("guiDeathName", "&7&lTod");
		msgDefaults.put("guiFortressSpawnName", "&7&lGarantierter Netherfestungsspawn");
		msgDefaults.put("guiNoDamageName", "&7&lKein Schaden");
		msgDefaults.put("guiNoRegName", "&7&lKeine Regeneration");
		msgDefaults.put("guiNoRegHardName", "&7&lKeine Regeneration");
		msgDefaults.put("guiCustomHealthName", "&7&lCustom Herzen");
		msgDefaults.put("guiSharedHealthName", "&7&lGeteilte Herzen");
		msgDefaults.put("guiNoPlacingName", "&7&lKein Platzieren");
		msgDefaults.put("guiNoBreakingName", "&7&lKein Abbauen");
		msgDefaults.put("guiNoCraftingName", "&7&lKein Crafting");
		msgDefaults.put("guiNoSneakingName", "&7&lKein Sneaking");
		msgDefaults.put("guiRandomBlockDropsName", "&7&Zufällige Blockdrops");
		msgDefaults.put("guiRandomMobDropsName", "&7&Zufällige Mobdrops");
		msgDefaults.put("guiRandomCraftingName", "&7&Zufälliges Crafting");
		
		msgDefaults.keySet().stream()
		.filter(key -> !cfg.isSet(key))
		.forEachOrdered(key -> cfg.set(key, msgDefaults.get(key)));
		
		saveCustomYml(cfg, file);
		msgDefaults.clear();
	}
}
