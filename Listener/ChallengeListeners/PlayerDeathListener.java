package ChallengeListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.Challenge.ChallengeEndReason;
import me.wand555.Challenge.Challenge.ChallengeProfile;
import me.wand555.Challenge.Challenge.Settings;

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
				ChallengeProfile.endChallenge(ChallengeEndReason.NATURAL_DEATH);
			}
		}
	}
}
