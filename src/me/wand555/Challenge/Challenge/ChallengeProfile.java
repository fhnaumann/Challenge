package me.wand555.Challenge.Challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import StartRunnables.DisplayActionBarTimer;
import StartRunnables.SecondTimer;
import me.wand555.Challenge.Util.DateUtil;

public class ChallengeProfile {
	
	private static final Challenge PLUGIN = Challenge.getPlugin(Challenge.class);
	
	private static HashSet<UUID> participants = new HashSet<UUID>();
		
	private static SecondTimer secondTimer;
	
	private static int sharedHPWaitDamageRunnableID = 0;
	private static int sharedHPWaitRegRunnableID = 0;
	/**
	 * Is always null, unless a challenge has concluded (death) and the server hasn't restarted.
	 */
	private static RestoreChallenge restoreChallenge;
	
	private static void checkConditionsAndApply() {
		HashSet<Player> players = fromUUIDToPlayer();
		if(Settings.isCustomHealth) {	
			players.stream()
			.forEach(p -> {					
				p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Settings.customHP);
				p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				p.setHealthScale(p.getHealth());
				p.setFoodLevel(15);
				p.getInventory().clear();
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
		ChallengeProfile.getSecondTimer().setMessage("PAUSED " + DateUtil.formatDuration(ChallengeProfile.getSecondTimer().getTime()) + "- /timer pause");
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
	
	
	
	public static void endChallenge(ChallengeEndReason reason) {
		HashSet<Player> players = fromUUIDToPlayer();
		restoreChallenge = new RestoreChallenge(players, secondTimer.getTime());
		//secondTimer.cancel();
		Settings.setDone();
		ChallengeProfile.getSecondTimer().stopIncreasing();
		ChallengeProfile.getSecondTimer().setMessage("Final Time: " + DateUtil.formatDuration(secondTimer.getTime()) + " - /challenge reset");
		players.stream().forEach(p -> {
			p.sendMessage(reason.getMessage());
			p.setGameMode(GameMode.SPECTATOR);
			p.sendMessage("Type /challenge reset to reset the challenge.");
		});
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
}
