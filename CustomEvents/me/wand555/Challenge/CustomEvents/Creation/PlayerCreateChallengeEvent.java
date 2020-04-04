package me.wand555.Challenge.CustomEvents.Creation;

import org.bukkit.entity.Player;

import org.bukkit.event.Event;

import org.bukkit.event.HandlerList;

public class PlayerCreateChallengeEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private Player p;
	
	/**
	 * Called when a player sucessfully starts to create a world.
	 * @param p The player
	 */
	public PlayerCreateChallengeEvent(Player p) {
		this.p = p;
	}
	
	public Player getPlayer() {
		return this.p;
	}
	
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}

	//Bukkit challenge
}
