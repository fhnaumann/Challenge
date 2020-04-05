package ChallengeListeners;

import org.bukkit.Material;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.ChallengeData.ChallengeProfile;
import me.wand555.Challenge.ChallengeData.Settings;

public class RandomizerListener implements Listener {

	public RandomizerListener(Challenge plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onRandomizeBlockDropEvent(BlockBreakEvent event) {
		if(Settings.isRandomizedBlockDrops) {
			if(Settings.canTakeEffect()) {
				if(event.isDropItems()) {
					event.setDropItems(false);
					Block block = event.getBlock();
					Object mat = ChallengeProfile.getRandomizeMapped().get(block.getType());
					ItemStack toDropItem = new ItemStack(mat != null ? (Material) mat : Material.AIR);
					if(toDropItem != null) {
						if(!toDropItem.getType().isAir() && toDropItem.getType().isItem()) {
							block.getWorld().dropItemNaturally(block.getLocation(), toDropItem);
						}
					}
				}	
			}
		}
	}
	
	@EventHandler
	public void onRandomizeMobDropEvent(EntityDeathEvent event) {
		if(Settings.isRandomizedMobDrops) {
			if(Settings.canTakeEffect()) {
				Entity entity = event.getEntity();
				if(entity instanceof Player) return;	
				event.getDrops().forEach(itemstack -> {
					Object mat = ChallengeProfile.getRandomizeMapped().get(itemstack.getType());
					ItemStack toDropItem = new ItemStack(mat != null ? (Material) mat : Material.AIR);
					if(!toDropItem.getType().isAir() && toDropItem.getType().isItem()) {
						entity.getWorld().dropItemNaturally(entity.getLocation(), toDropItem);
					}
				});
				event.getDrops().clear();		
			}
		}	
	}
	
	@EventHandler
	public void onRandomizeCraftingResult(PrepareItemCraftEvent event) {
		if(Settings.isRandomizedCrafting) {
			if(Settings.canTakeEffect()) {
				if(event.getRecipe() != null) {
					if(event.getRecipe().getResult() != null) {
						ItemStack result = event.getRecipe().getResult();
						Object random = ChallengeProfile.getRandomizeMapped().get(result.getType());
						int craftingAmount = result.getAmount();
						ItemStack toDisplayItem = new ItemStack(random != null ? (Material) random : Material.AIR);		
						if(!toDisplayItem.getType().isAir() && toDisplayItem.getType().isItem()) {
							toDisplayItem.setAmount(craftingAmount <= toDisplayItem.getMaxStackSize() ? 
									craftingAmount : toDisplayItem.getMaxStackSize());
							event.getInventory().setResult(toDisplayItem);
						}
					}			
				}		
			}
		}
	}
	
	@EventHandler
	public void onRandomizeFurnaceResult(FurnaceSmeltEvent event) {
		if(Settings.isRandomizedCrafting) {
			if(Settings.canTakeEffect()) {
				ItemStack result = event.getResult();
				Object random = ChallengeProfile.getRandomizeMapped().get(result.getType());
				int craftingAmount = result.getAmount();
				ItemStack toDisplayItem = new ItemStack(random != null ? (Material) random : Material.AIR);		
				if(!toDisplayItem.getType().isAir() && toDisplayItem.getType().isItem()) {
					toDisplayItem.setAmount(craftingAmount <= toDisplayItem.getMaxStackSize() ? 
							craftingAmount : toDisplayItem.getMaxStackSize());
					event.setResult(toDisplayItem);
				}
			}
		}
	}
}
