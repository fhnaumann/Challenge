package ChallengeListeners;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.ChallengeData.ChallengeEndReason;
import me.wand555.Challenge.ChallengeData.ChallengeProfile;
import me.wand555.Challenge.ChallengeData.Settings;

public class NoBlockPlaceListener implements Listener {

	private Challenge plugin;
	
	public NoBlockPlaceListener(Challenge plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {
		if(Settings.noBlockPlace) {
			if(Settings.canTakeEffect()) {				
				Material mat = event.getBlockPlaced().getType();
				if(mat != Material.LAVA 
						&& mat != Material.WATER
						&& mat != Material.END_PORTAL_FRAME
						&& !(mat == Material.FIRE 
							&& event.getBlock().getRelative(BlockFace.DOWN).getType() == Material.OBSIDIAN)) {
					ChallengeProfile.endChallenge(ChallengeEndReason.NO_BLOCK_PLACE);
				}
			}
			else {
				event.setCancelled(true);
			}
		}
	}
}
