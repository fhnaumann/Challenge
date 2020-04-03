package GUI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import com.google.common.collect.Lists;

import GUIType.GUIType;

import static GUIType.GUIType.*;

import java.util.ArrayList;
import java.util.Arrays;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.Challenge.ChallengeProfile;
import me.wand555.Challenge.Challenge.Settings;

public class GUI {
	
	private Challenge plugin;
	
	public GUI(Challenge plugin) {
		this.plugin = plugin;
	}
	
	public void createGUI(Player p, GUIType type) {
		Inventory gui = null;
		if(type == OVERVIEW) {
			gui = plugin.getServer().createInventory(null, 36, "Overview");
			for(int i=0; i<gui.getSize(); i++) {
				switch(i) {
				case 0:
					gui.setItem(i, createItem(Material.PLAYER_HEAD, "Death", 
							new ArrayList<String>(Arrays.asList("The challenge is over when someone dies!")), 
							Settings.endOnDeath));
					break;
				case 1:
					gui.setItem(i, createItem(Material.BLAZE_ROD, "Guarantee Fortress Spawn", 
							Lists.newArrayList("Guarantees a portal spawn", 
									"near/inside a fortress!", "Might lag the server for the first teleportation!"), 
							Settings.spawnNearFortress));
					break;
				case 2:
					gui.setItem(i, createItem(Material.WITHER_ROSE, "No Damage", 
							new ArrayList<String>(Arrays.asList("No one is allowed to", "take damage!")), 
							Settings.noDamage));
					break;
				case 3:
					gui.setItem(i, createPotionItem(Material.POTION, PotionType.REGEN, "No Regeneration", 
							new ArrayList<String>(Arrays.asList("No one regenerates health regularly!")), 
							Settings.noReg));
					break;
				case 4:
					gui.setItem(i, createPotionItem(Material.POTION, PotionType.REGEN, "No Regeneration", 
							new ArrayList<String>(Arrays.asList("No one regenerates health at all!", "Overrides \"No Regeneration\"")), 
							Settings.noRegHard));
					break;
				case 5:
					gui.setItem(i, createItemHealthLore(Material.COMMAND_BLOCK, "Custom Health", 
							new ArrayList<String>(Arrays.asList("Set the max. health for all players!", "1 Heart = 2HP")), 
							Settings.isCustomHealth));
					break;
				case 6:
					gui.setItem(i, createItem(Material.GHAST_TEAR, "Shared Health", 
							new ArrayList<String>(Arrays.asList("Health will be shared across all players!")), 
							Settings.isSharedHealth));
					break;
				case 7:
					gui.setItem(i, createItem(Material.GRASS_BLOCK, "No Block Placing", 
							Lists.newArrayList("Block placing is forbidden!", "Exception:", "Ender eyes", "Water/Lava bucket", "Flint and Steel when lighting up a portal"), 
							Settings.noBlockPlace));
					break;
				case 8:
					gui.setItem(i, createItem(Material.STONE, "No Block Breaking", 
							Lists.newArrayList("Block breaking is forbidden!"), Settings.noBlockBreaking));
					break;
				case 9:
					gui.setItem(i, createItem(Material.CRAFTING_TABLE, "No Crafting", 
							Lists.newArrayList("Crafting is forbidden"), 
							Settings.noCrafting));
					break;
				case 10:
					gui.setItem(i, createItem(Material.STONE_PRESSURE_PLATE, "No Sneaking", 
							Lists.newArrayList("Sneaking is forbidden"), Settings.noSneaking));
					break;
				default:
					gui.setItem(i, createGlass());
				}
			}
		}
		p.openInventory(gui);
	}
	
	private ItemStack createItem(Material mat, String name, ArrayList<String> lore, boolean enabled) {
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if(enabled) {
			meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			lore.add(ChatColor.GREEN + "Enabled");
		}
		else {
			lore.add(ChatColor.RED + "Disabled");
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	private ItemStack createItemHealthLore(Material mat, String name, ArrayList<String> lore, boolean enabled) {
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if(enabled) {
			meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			lore.add(ChatColor.GREEN + "Enabled");
			lore.add("Max HP: " + Settings.customHP);
		}
		else {
			lore.add(ChatColor.RED + "Disabled");
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	private ItemStack createPotionItem(Material mat, PotionType type, String name, ArrayList<String> lore, boolean enabled) {
		ItemStack item = new ItemStack(mat);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.setBasePotionData(new PotionData(type));
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName(name);
		if(enabled) {
			meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			lore.add(ChatColor.GREEN + "Enabled");
		}
		else {
			lore.add(ChatColor.RED + "Disabled");
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	private ItemStack createGlass() {
		ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		return item;
	}
}
