package ChallengeListeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.Challenge.ChallengeEndReason;
import me.wand555.Challenge.Challenge.ChallengeProfile;
import me.wand555.Challenge.Challenge.Settings;

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
