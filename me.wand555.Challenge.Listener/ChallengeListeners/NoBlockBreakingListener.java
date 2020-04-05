package ChallengeListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.ChallengeData.ChallengeEndReason;
import me.wand555.Challenge.ChallengeData.ChallengeProfile;
import me.wand555.Challenge.ChallengeData.Settings;

public class NoBlockBreakingListener implements Listener {

	private Challenge plugin;
	
	public NoBlockBreakingListener(Challenge plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockbreakEvent(BlockBreakEvent event) {
		if(Settings.noBlockBreaking) {
			if(Settings.canTakeEffect()) {
				ChallengeProfile.endChallenge(ChallengeEndReason.NO_BLOCK_BREAK);
			}
			else {
				event.setCancelled(true);
			}
		}
	}
}
