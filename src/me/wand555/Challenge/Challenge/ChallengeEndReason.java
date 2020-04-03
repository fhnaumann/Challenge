package me.wand555.Challenge.Challenge;

public enum ChallengeEndReason {

	FINISHED("The challenge has been completed!"),
	NATURAL_DEATH("A player has died! The challenge ended!"),
	NO_DAMAGE("A player has taken damage! The challenge ended!"),
	NO_BLOCK_PLACE("A player has placed a block! The challenge ended!"),
	NO_BLOCK_BREAK("A player has broken a block! The challenge ended!"),
	NO_CRAFTING("A player has crafted an item! The challenge ended!"), 
	NO_SNEAKING("A player has sneaked! The challenge ended!")
	;
	
	private final String message;
	
	private ChallengeEndReason(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
