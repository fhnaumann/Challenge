package me.wand555.Challenge.Challenge;

import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class PositionManager {

	public static HashSet<Position> positions = new HashSet<Position>();
	
	public static void addToPosition(Position pos) {
		positions.add(pos);
	}
	
	public static String displayPosition(Position pos) {
		return "Position: " + ChatColor.GREEN + pos.getName() + ChatColor.GRAY +" at " + ChatColor.GREEN 
				+ displayLocation(pos.getLocation()) + ChatColor.GRAY + " (" + ChatColor.GREEN
				+ pos.getDate() + ChatColor.GRAY + " by " + ChatColor.GREEN + Bukkit.getOfflinePlayer(pos.getCreator()).getName()
				+ ChatColor.GRAY + ")";
	}
	
	public static boolean positionWithNameExists(String name) {
		return positions.stream().anyMatch(pos -> pos.getName().equalsIgnoreCase(name));
	}
	
	public static Position getPositionFromName(String name) {
		return positions.stream().filter(pos -> pos.getName().equalsIgnoreCase(name)).findFirst().get();
	}
	
	public static HashSet<Position> getAllPositionsFromUUID(UUID uuid) {
		return positions.stream().filter(pos -> pos.getCreator().equals(uuid)).collect(Collectors.toCollection(HashSet::new));
	}
	
	private static String displayLocation(Location loc) {
		return loc.getWorld().getName()+"/"+loc.getBlockX()+"/"+loc.getBlockY()+"/"+loc.getBlockZ();
	}
}
