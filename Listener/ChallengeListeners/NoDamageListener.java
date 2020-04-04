package ChallengeListeners;

import org.bukkit.entity.EntityType;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.Challenge.ChallengeEndReason;
import me.wand555.Challenge.Challenge.ChallengeProfile;
import me.wand555.Challenge.Challenge.Settings;

public class NoDamageListener implements Listener {

	private Challenge plugin;
	
	public NoDamageListener(Challenge plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void onPlayerTakeDamageEvent(EntityDamageEvent event) {
		if(Settings.noDamage) {
			if(Settings.canTakeEffect()) {
				if(event.getEntityType() == EntityType.PLAYER) {
					Player p = (Player) event.getEntity();
					ChallengeProfile.endChallenge(ChallengeEndReason.NO_DAMAGE);
				}
			}	
			else {
				event.setCancelled(true);
			}
		}
	}
}
