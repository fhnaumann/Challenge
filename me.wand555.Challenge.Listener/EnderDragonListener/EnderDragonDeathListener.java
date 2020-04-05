package EnderDragonListener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.ChallengeData.ChallengeEndReason;
import me.wand555.Challenge.ChallengeData.ChallengeProfile;
import me.wand555.Challenge.WorldLinking.WorldLinkManager;

public class EnderDragonDeathListener implements Listener {

	private Challenge plugin;
	
	public EnderDragonDeathListener(Challenge plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEnderDragonDeathEvent(EntityDeathEvent event) {
		if(event.getEntityType() == EntityType.ENDER_DRAGON) {
			if(WorldLinkManager.worlds.contains(event.getEntity().getWorld())) {
				ChallengeProfile.endChallenge(ChallengeEndReason.FINISHED);
			}
		}
	}
}
