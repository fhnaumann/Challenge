package ChallengeListeners;

import org.bukkit.World.Environment;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.ChallengeData.ChallengeEndReason;
import me.wand555.Challenge.ChallengeData.ChallengeProfile;
import me.wand555.Challenge.ChallengeData.Settings;
import me.wand555.Challenge.WorldLinking.WorldLinkManager;

public class PlayerDeathListener implements Listener {

	private Challenge plugin;
	
	public PlayerDeathListener(Challenge plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeathEvent(PlayerDeathEvent event) {
		if(Settings.endOnDeath) {
			if(Settings.canTakeEffect()) {
				ChallengeProfile.endChallenge(event.getEntity(), ChallengeEndReason.NATURAL_DEATH);
			}
		}
	}
	
	@EventHandler
	public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
		if(WorldLinkManager.worlds.contains(event.getPlayer().getLocation().getWorld())) {
			if(event.isBedSpawn()) {
				if(event.getRespawnLocation() != null) {
					if(!WorldLinkManager.worlds.contains(event.getRespawnLocation().getWorld())) {
						event.setRespawnLocation(WorldLinkManager.worlds.stream()
								.filter(w -> w.getEnvironment() == Environment.NORMAL)
								.findFirst().get()
								.getSpawnLocation());
					}
				}
				else {
					event.setRespawnLocation(WorldLinkManager.worlds.stream()
							.filter(w -> w.getEnvironment() == Environment.NORMAL)
							.findFirst().get()
							.getSpawnLocation());
				}
			}
			else {
				event.setRespawnLocation(WorldLinkManager.worlds.stream()
						.filter(w -> w.getEnvironment() == Environment.NORMAL)
						.findFirst().get()
						.getSpawnLocation());
			}
		}
	}
}
