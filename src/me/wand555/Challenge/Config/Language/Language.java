package me.wand555.Challenge.Config.Language;

public enum Language {
ENGLISH("en"),
GERMAN("de")
;
	
	private final String abrv;

	Language(String abrv) {
		this.abrv = abrv;
	}
	
	public String getAbbreviation() {
		return this.abrv; 
	}
	
}
