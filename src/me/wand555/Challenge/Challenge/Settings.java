package me.wand555.Challenge.Challenge;

public class Settings {
	
	public static boolean hasStarted = false;
	public static void setStarted() {hasStarted = !hasStarted;}
	public static boolean isPaused = false;
	public static void setPaused() {isPaused = !isPaused;}
	/**
	 * Needs to be true to be able to perform /challenge reset
	 * True when challenge failed or completed.
	 */
	public static boolean isDone = false;
	public static void setDone() {isDone = !isDone;}
	
	/**
	 * Utility method so on action I can only call this
	 * @return if effect is valid to take place
	 */
	public static boolean canTakeEffect() {return hasStarted && !isPaused && !isDone;}
	
	public static boolean endOnDeath = true;
	public static void setEndOnDeath() {endOnDeath = !endOnDeath;}
	
	public static boolean spawnNearFortress = false;
	public static void setSpawnNearFortress() {spawnNearFortress = !spawnNearFortress;}
	
	public static boolean noDamage = false;
	public static void setNoDamage() {noDamage = !noDamage;}
	
	public static boolean noReg = false;
	public static void setNoReg() {noReg = !noReg;}
	
	public static boolean noRegHard = false;
	public static void setNoRegHard() {noRegHard = !noRegHard;}
	
	public static boolean isCustomHealth = false;
	public static void setIsCustomHealth() {isCustomHealth = !isCustomHealth;}
	public static int customHP = 20;
	public static void setCustomHP(int amount) {customHP = amount;}
	
	public static boolean isSharedHealth = false;
	public static void setIsSharedHealth() {isSharedHealth = !isSharedHealth;}
	public static double sharedHP = customHP;
	public static void setSharedHP(double amount) {sharedHP = amount;}
	
	public static boolean noBlockPlace = false;
	public static void setNoBlockPlace() {noBlockPlace = !noBlockPlace;}
	
	public static boolean noBlockBreaking = false;
	public static void setNoBlockBreaking() {noBlockBreaking = !noBlockBreaking;}
	
	public static boolean noCrafting = false;
	public static void setNoCrafting() {noCrafting = !noCrafting;}
	
	public static boolean noSneaking = false;
	public static void setNoSneaking() {noSneaking = !noSneaking;}
	
	public static void restoreDefault() {
		hasStarted = false;
		isPaused = false;
		isDone = false;
		endOnDeath = true;
		noDamage = false;
		//implement rest later...
	}
}
