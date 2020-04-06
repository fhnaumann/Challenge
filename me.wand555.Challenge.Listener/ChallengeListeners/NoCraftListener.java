package ChallengeListeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.ChallengeData.ChallengeEndReason;
import me.wand555.Challenge.ChallengeData.ChallengeProfile;
import me.wand555.Challenge.ChallengeData.Settings;

public class NoCraftListener implements Listener {

	private Challenge plugin;
	
	public NoCraftListener(Challenge plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onCraftingEvent(InventoryClickEvent event) {
		if(Settings.noCrafting) {
			if(Settings.canTakeEffect()) {
				if(event.getView().getType() == InventoryType.CRAFTING || event.getView().getType() == InventoryType.WORKBENCH) {
					//player clicked result slot
					if(event.getRawSlot() == 0) {
						System.out.println(event.getCurrentItem());
						if(event.getCurrentItem() != null) {
							if(event.getCurrentItem().getType() != Material.AIR) {
								ChallengeProfile.endChallenge((Player)event.getWhoClicked(), ChallengeEndReason.NO_CRAFTING);
							}
						}
					}
				}
			}
		}
	}
}
