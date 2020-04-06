package ChallengeListeners;

import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.ChallengeData.ChallengeEndReason;
import me.wand555.Challenge.ChallengeData.ChallengeProfile;
import me.wand555.Challenge.ChallengeData.Settings;

public class NoSneakingListener implements Listener {

	private Challenge plugin;
	
	public NoSneakingListener(Challenge plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onSneakingEvent(PlayerToggleSneakEvent event) {
		if(Settings.noSneaking) {
			if(Settings.canTakeEffect()) {
				ChallengeProfile.endChallenge(event.getPlayer(), ChallengeEndReason.NO_SNEAKING);
			}
		}
	}
}
