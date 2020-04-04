package me.wand555.Challenge.Challenge;


import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;

public class RestoreChallenge {

	private HashMap<UUID, RestorePlayerData> participants = new HashMap<UUID, RestorePlayerData>();
	private long oldTimer;
	
	public RestoreChallenge(HashSet<Player> players, long timer) {
		players.stream().forEach(p -> {
			participants.put(p.getUniqueId(), new RestorePlayerData(p));
		});
		this.oldTimer = timer;
	}
	
	public void restoreChallenge() {
		//set inv contents and locations
		//clear the hashmap (and set timer to 0)
		ChallengeProfile.getSecondTimer().setTime(oldTimer);
		Settings.setDone();
		Settings.setPaused();
		ChallengeProfile.resumeTimer();	
		participants.entrySet().stream()
			.forEach(entry -> {
				Player p = Bukkit.getPlayer(entry.getKey());
				if(p == null) return;
				p.teleport(entry.getValue().getPlayerLoc(), TeleportCause.PLUGIN);
				p.setGameMode(GameMode.SURVIVAL);
				p.getInventory().setContents(entry.getValue().getInvContent().toArray(new ItemStack[entry.getValue().getInvContent().size()]));
				p.sendMessage(Challenge.PREFIX + ChatColor.GRAY + "You received your items back and were teleported to the location when the challenge ended.");
			});
		participants.clear();
		this.oldTimer = 0;
	}
}
