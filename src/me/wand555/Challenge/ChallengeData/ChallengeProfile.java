package me.wand555.Challenge.ChallengeData;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import StartRunnables.SecondTimer;
import StartRunnables.TimerMessage;
import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.ChallengeData.Restore.RestoreChallenge;
import me.wand555.Challenge.Config.Language.LanguageMessages;
import me.wand555.Challenge.Util.DateUtil;

public class ChallengeProfile {
	
	private static final Challenge PLUGIN = Challenge.getPlugin(Challenge.class);
	
	private static HashSet<UUID> participants = new HashSet<UUID>();
		
	private static SecondTimer secondTimer;
	
	private static int sharedHPWaitDamageRunnableID = 0;
	private static int sharedHPWaitRegRunnableID = 0;
	
	private static HashMap<Material, Material> randomizeMapped = new HashMap<>();
	//technically final
	private static final Material[] NORMAL_MATERIALS = Material.values();
	
	/**
	 * Is always null, unless a challenge has concluded (death) and the server hasn't restarted.
	 */
	private static RestoreChallenge restoreChallenge;
	
	private static void checkConditionsAndApply() {
		HashSet<Player> players = fromUUIDToPlayer();
		System.out.println("Right before size: " + players.size());
		if(Settings.isCustomHealth) {	
			players.stream()
			.forEach(p -> {					
				p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Settings.customHP);
				p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				p.setHealthScale(p.getHealth());
				p.setFoodLevel(15);
				//p.getInventory().clear();
			});
		}
		else {
			Settings.setCustomHP(20);
			players.stream()
			.forEach(p -> {					
				p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Settings.customHP);
				//p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				p.setHealthScale(p.getHealth());
			});
		}
		
		if(Settings.isRandomizedBlockDrops
				|| Settings.isRandomizedMobDrops
				|| Settings.isRandomizedCrafting) {
			if(randomizeMapped == null || randomizeMapped.isEmpty()) {
				randomizeMapped = Lists.newArrayList(NORMAL_MATERIALS).stream()
						.filter(mat -> !mat.isAir())
						.collect(Collectors.toMap(Function.identity(), 
								mat -> NORMAL_MATERIALS[ThreadLocalRandom.current().nextInt(0, NORMAL_MATERIALS.length)], 
								(v1, v2) -> v1, 
								HashMap::new));
			}						
		}
		else {
			randomizeMapped.clear();
		}
	}
	
	public static void restoreChallenge() {
		if(restoreChallenge != null) {
			restoreChallenge.restoreChallenge();
			
		}
	}
	
	public static void onReset() {
		participants.clear();
		for(Player player : fromUUIDToPlayer()) {
			//maybe store on challenge creation and load here?
			player.setGameMode(GameMode.SURVIVAL);
			//Bukkit.getBossBars().next().getTitle().equalsIgnoreCase("Ender Dragon"); //remove etc...
		}
		secondTimer = null;
		restoreChallenge = null;
	}
	
	public static void startTimer() {
		//REMOVE LATER::
		//participants = Bukkit.getOnlinePlayers().stream().map(p -> p.getUniqueId()).collect(Collectors.toCollection(HashSet::new));
		
		//if(ChallengeProfile.secondTimer == null) new SecondTimer(PLUGIN, fromUUIDToPlayer(), 0);
		//else secondTimer.cancel();
		//::
		
		//usually cancel actionBarTimer
		ChallengeProfile.getSecondTimer().startIncreasing();
		Settings.setStarted();
		checkConditionsAndApply();
	}
	
	public static void pauseTimer() {
		ChallengeProfile.getSecondTimer().stopIncreasing();
		ChallengeProfile.getSecondTimer().setMessageType(TimerMessage.TIMER_PAUSED);
		Settings.setPaused();
	}
	
	public static void pauseTimerOnDisable() {
		secondTimer.cancel();
		Settings.setPaused();
	}
	
	public static void resumeTimer() {
		ChallengeProfile.getSecondTimer().startIncreasing();
		Settings.setPaused();
		checkConditionsAndApply();	
	}
	
	
	
	public static void endChallenge(Player causer, ChallengeEndReason reason) {
		HashSet<Player> players = fromUUIDToPlayer();
		restoreChallenge = new RestoreChallenge(players, secondTimer.getTime());
		Settings.setDone();
		ChallengeProfile.getSecondTimer().stopIncreasing();
		ChallengeProfile.getSecondTimer().setMessageType(TimerMessage.TIMER_FINISHED);
		String reasonMessage = "";
		switch(reason) {
		case FINISHED:
			reasonMessage = LanguageMessages.endChallengeComplete.replace("[TIME]", DateUtil.formatDuration(secondTimer.getTime()));
			break;
		case NATURAL_DEATH:
			reasonMessage = LanguageMessages.endChallengeNaturalDeath.replace("[PLAYER]", causer.getName());
			break;
		case NO_BLOCK_BREAK:
			reasonMessage = LanguageMessages.endChallengeNoBreak.replace("[PLAYER]", causer.getName());
			break;
		case NO_BLOCK_PLACE:
			reasonMessage = LanguageMessages.endChallengeNoPlace.replace("[PLAYER]", causer.getName());
			break;
		case NO_CRAFTING:
			reasonMessage = LanguageMessages.endChallengeNoCrafting.replace("[PLAYER]", causer.getName());
			break;
		case NO_DAMAGE:
			reasonMessage = LanguageMessages.endChallengeNoDamage.replace("[PLAYER]", causer.getName());
			break;
		case NO_SNEAKING:
			reasonMessage = LanguageMessages.endChallengeNoSneaking.replace("[PLAYER]", causer.getName());
			break;
		default:
			break;
		
		}
		
		for(Player p : players) {
			p.sendMessage(reasonMessage);
			p.setGameMode(GameMode.SPECTATOR);
			p.sendMessage(Challenge.PREFIX + ChatColor.GRAY + "Type /challenge reset to reset the challenge.");
		}
	}
	
	public static SecondTimer getSecondTimer() {
		return secondTimer;
	}
	
	public static void addToParticipants(UUID uuid) {
		participants.add(uuid);
	}

	public static void removeFromParticipants(UUID uuid) {
		participants.remove(uuid);
	}

	public static void sendMessageToAllParticipants(String msg) {
		participants.stream().forEach(key -> Bukkit.getPlayer(key).sendMessage(msg));
	}
	
	public static boolean isInChallenge(UUID uuid) {
		return participants.stream().anyMatch(key -> key.equals(uuid));
	}
	
	public static HashSet<Player> fromUUIDToPlayer() {
		return participants.stream().map(Bukkit::getPlayer).collect(Collectors.toCollection(HashSet::new));
	}

	/**
	 * @return the participants
	 */
	public static HashSet<UUID> getParticipants() {
		return participants;
	}

	/**
	 * @param participants the participants to set
	 */
	public static void setParticipants(HashSet<UUID> participants) {
		ChallengeProfile.participants = participants;
	}

	/**
	 * @return the sharedHPWaitDamageRunnableID
	 */
	public static int getSharedHPWaitDamageRunnableID() {
		return sharedHPWaitDamageRunnableID;
	}

	/**
	 * @param sharedHPWaitDamageRunnableID the sharedHPWaitDamageRunnableID to set
	 */
	public static void setSharedHPWaitDamageRunnableID(int sharedHPWaitDamageRunnableID) {
		ChallengeProfile.sharedHPWaitDamageRunnableID = sharedHPWaitDamageRunnableID;
	}

	/**
	 * @return the sharedHPWaitRegRunnableID
	 */
	public static int getSharedHPWaitRegRunnableID() {
		return sharedHPWaitRegRunnableID;
	}

	/**
	 * @param sharedHPWaitRegRunnableID the sharedHPWaitRegRunnableID to set
	 */
	public static void setSharedHPWaitRegRunnableID(int sharedHPWaitRegRunnableID) {
		ChallengeProfile.sharedHPWaitRegRunnableID = sharedHPWaitRegRunnableID;
	}

	/**
	 * @param secondTimer the secondTimer to set
	 */
	public static void setSecondTimer(SecondTimer secondTimer) {
		ChallengeProfile.secondTimer = secondTimer;
	}

	/**
	 * @return the randomizeMapped
	 */
	public static HashMap<Material, Material> getRandomizeMapped() {
		return randomizeMapped;
	}
	
	public static void setRandomizeMapped(HashMap<Material, Material> mapped) {
		randomizeMapped = mapped;
	}
}
