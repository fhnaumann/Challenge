package me.wand555.Challenge.ChallengeData.Backpack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import me.wand555.Challenge.Challenge.Challenge;

public class BackPack {

	public static final int BACKPACK_SIZE = 36;
	private static final Challenge PLUGIN = Challenge.getPlugin(Challenge.class);
	private static HashSet<UUID> openPlayers = new HashSet<UUID>();
	private static ItemStack[] contents = new ItemStack[36];

	
	public static void removeFromOpenPlayers(UUID uuid) {
		openPlayers.remove(uuid);
	}
	
	public static void addToOpenPlayers(UUID uuid) {
		openPlayers.add(uuid);
	}
	
	public static void updateInventories() {
		openPlayers.stream()
		.map(Bukkit::getPlayer)
		.forEach(p -> Bukkit.getScheduler()
				.runTaskLater(PLUGIN, () -> p.updateInventory(), 1L));
	}
	
	/**
	 * @return the openPlayers
	 */
	public static HashSet<UUID> getOpenPlayers() {
		return openPlayers;
	}
	/**
	 * @param openPlayers the openPlayers to set
	 */
	public static void setOpenPlayers(HashSet<UUID> openPlayers) {
		BackPack.openPlayers = openPlayers;
	}
	/**
	 * @return the contents
	 */
	public static ItemStack[] getContents() {
		return contents;
	}
	/**
	 * @param contents the contents to set
	 */
	public static void setContents(ItemStack[] contents) {
		BackPack.contents = contents;
	}
	
}
