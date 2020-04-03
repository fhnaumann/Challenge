package ChallengeListeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.Challenge.Settings;

/**
 * Listens to noReg and noRegHard
 * @author Felix
 *
 */
public class NoRegenListener implements Listener {

	private Challenge plugin;
	
	public NoRegenListener(Challenge plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onRegenEvent(EntityRegainHealthEvent event) {
		if(Settings.noRegHard) {
			if(Settings.canTakeEffect()) {
				if(event.getEntityType() == EntityType.PLAYER) {
					event.setCancelled(true);			
				}
			}
			else {
				event.setCancelled(true);
			}
		}	
	}
}
