package me.wand555.Challenge.Config;

import me.wand555.Challenge.Challenge.Challenge;

public class UserConfig extends ConfigUtil {

	public static final Challenge PLUGIN = Challenge.getPlugin(Challenge.class);
	
	public static void setUpDefaultConfig() {
		PLUGIN.getConfig().addDefault("", "#Supported Languages:");
		PLUGIN.getConfig().addDefault("", "#English - " + Language.ENGLISH.getAbbreviation());
		PLUGIN.getConfig().addDefault("", "#German - " + Language.GERMAN.getAbbreviation());
		PLUGIN.getConfig().addDefault("Language", "en");
		PLUGIN.saveDefaultConfig();
	}
	
	public static Language setUpLanguage() {
		String lang = PLUGIN.getConfig().getString("Language");
		if(lang.equalsIgnoreCase(Language.ENGLISH.getAbbreviation())) {
			new LanguageMessageEnglish();
			return Language.ENGLISH;
		}
		else if(lang.equalsIgnoreCase(Language.GERMAN.getAbbreviation())) {
			return Language.GERMAN;
		}
		return Language.ENGLISH;
	}
}
