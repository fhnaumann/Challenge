package StartListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import me.wand555.Challenge.Challenge.Challenge;

/**
 * Will be hopefully removed later.
 * @author wand555
 *
 */
public class PlayerTeleportWorldListener implements Listener {

	private Challenge plugin;
	
	public PlayerTeleportWorldListener(Challenge plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
}
