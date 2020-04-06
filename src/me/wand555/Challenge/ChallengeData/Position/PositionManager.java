package me.wand555.Challenge.ChallengeData.Position;

import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import me.wand555.Challenge.Config.Language.LanguageMessages;

public class PositionManager {

	public static HashSet<Position> positions = new HashSet<Position>();
	
	public static void addToPosition(Position pos) {
		positions.add(pos);
	}
	
	public static String displayPosition(Position pos) {
		Location loc = pos.getLocation();
		return LanguageMessages.returnPosition
				.replace("[X]", String.valueOf(loc.getBlockX()))
				.replace("[Y]", String.valueOf(loc.getBlockY()))
				.replace("[Z]", String.valueOf(loc.getBlockZ()))
				.replace("[POSNAME]", pos.getName())
				.replace("[WORLD]", loc.getWorld().getName());
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
