package me.wand555.Challenge.ChallengeData;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldPlayerData {

	private UUID uuid;
	private Location overworldLoc;
	private Location netherLoc;
	private Location endLoc;
	
	public WorldPlayerData(Player p) {
		this.uuid = p.getUniqueId();
		this.overworldLoc = p.getLocation();
	}
	
	/**
	 * Should always return true, because this is the world they connect to when they create/join
	 * @return
	 */
	public boolean hasOverWorldLoc() {
		return overworldLoc != null;
	}
	
	public boolean hasNetherLoc() {
		return netherLoc != null;
	}
	
	public boolean hasEndLoc() {
		return endLoc != null;
	}

	/**
	 * @return the overworldLoc
	 */
	public Location getOverworldLoc() {
		return overworldLoc;
	}

	/**
	 * @param overworldLoc the overworldLoc to set
	 */
	public void setOverworldLoc(Location overworldLoc) {
		this.overworldLoc = overworldLoc;
	}

	/**
	 * @return the netherLoc
	 */
	public Location getNetherLoc() {
		return netherLoc;
	}

	/**
	 * @param netherLoc the netherLoc to set
	 */
	public void setNetherLoc(Location netherLoc) {
		this.netherLoc = netherLoc;
	}

	/**
	 * @return the endLoc
	 */
	public Location getEndLoc() {
		return endLoc;
	}

	/**
	 * @param endLoc the endLoc to set
	 */
	public void setEndLoc(Location endLoc) {
		this.endLoc = endLoc;
	}

	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}
}
