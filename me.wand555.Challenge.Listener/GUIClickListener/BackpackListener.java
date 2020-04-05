package GUIClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.ChallengeData.Backpack.BackPack;

public class BackpackListener implements Listener {

	private Challenge plugin;
	
	public BackpackListener(Challenge plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void t(InventoryDragEvent event) {
		if(event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Backpack")) {
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			//BackPack.setContents(contents);
		}
	}
	
	@EventHandler
	public void onBackpackClickEvent(InventoryClickEvent event) {
		if(event.getClickedInventory() != null) {
			if(event.getWhoClicked() instanceof Player) {	
				if(event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Backpack")) {
					new BukkitRunnable() {
						
						@Override
						public void run() {
							//System.out.println(Arrays.toString(event.getView().getTopInventory().getContents()));
							BackPack.setContents(event.getWhoClicked().getOpenInventory().getTopInventory().getContents());
							BackPack.updateInventories();
						}
					}.runTaskLater(plugin, 1L);
					
				}
			}
		}
	}
	
	@EventHandler
	public void onBackpackOpenEvent(InventoryOpenEvent event) {
		if(event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Backpack")) {
			BackPack.addToOpenPlayers(event.getPlayer().getUniqueId());
		}
	}
	
	@EventHandler
	public void onBackpackCloseEvent(InventoryCloseEvent event) {
		if(event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Backpack")) {
			BackPack.removeFromOpenPlayers(event.getPlayer().getUniqueId());
		}
	}
}
