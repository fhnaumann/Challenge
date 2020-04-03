package ChallengeProfileListener;

import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.wand555.Challenge.Challenge.Challenge;

import me.wand555.Challenge.Challenge.ChallengeProfile;

public class PlayerQuitListener implements Listener {

	private Challenge plugin;
	
	public PlayerQuitListener(Challenge plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		if(ChallengeProfile.isInChallenge(event.getPlayer().getUniqueId())) {
			ChallengeProfile.removeFromParticipants(event.getPlayer().getUniqueId());
		}
	}
}
