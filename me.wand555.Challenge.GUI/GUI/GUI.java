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
import me.wand555.Challenge.ChallengeData.Settings;
import me.wand555.Challenge.ChallengeData.Backpack.BackPack;
import me.wand555.Challenge.Config.Language.LanguageMessages;

public class GUI {
	
	private Challenge plugin;
	
	public GUI(Challenge plugin) {
		this.plugin = plugin;
	}
	
	public void createGUI(Player p, GUIType type) {
		Inventory gui = null;
		if(type == OVERVIEW) {
			gui = plugin.getServer().createInventory(null, 36, ChatColor.GREEN + "Settings");
			for(int i=0; i<gui.getSize(); i++) {
				switch(i) {
				case 0:
					gui.setItem(i, createItem(Material.PLAYER_HEAD, LanguageMessages.guiDeathName, 
							new ArrayList<String>(Arrays.asList(ChatColor.GRAY + "The challenge is over when someone dies!")), 
							Settings.endOnDeath));
					break;
				case 1:
					gui.setItem(i, createItem(Material.BLAZE_ROD, LanguageMessages.guiFortressSpawnName, 
							Lists.newArrayList(ChatColor.GRAY + "Guarantees a portal spawn", 
									ChatColor.GRAY + "near/inside a fortress!", 
									ChatColor.GRAY + "Might lag the server for the first teleportation!"), 
							Settings.spawnNearFortress));
					break;
				case 2:
					gui.setItem(i, createItem(Material.WITHER_ROSE, LanguageMessages.guiNoDamageName, 
							new ArrayList<String>(Arrays.asList(ChatColor.GRAY + "No one is allowed to", 
									ChatColor.GRAY + "take damage!")), 
							Settings.noDamage));
					break;
				case 3:
					gui.setItem(i, createPotionItem(Material.POTION, PotionType.REGEN, LanguageMessages.guiNoRegName, 
							new ArrayList<String>(Arrays.asList(ChatColor.GRAY + "No one regenerates health regularly!")), 
							Settings.noReg));
					break;
				case 4:
					gui.setItem(i, createPotionItem(Material.POTION, PotionType.REGEN, LanguageMessages.guiNoRegHardName, 
							new ArrayList<String>(Arrays.asList(ChatColor.GRAY + "No one regenerates health at all!", 
									ChatColor.GRAY + "Overrides \"No Regeneration\"")), 
							Settings.noRegHard));
					break;
				case 5:
					gui.setItem(i, createItemHealthLore(Material.COMMAND_BLOCK, LanguageMessages.guiCustomHealthName, 
							new ArrayList<String>(Arrays.asList(ChatColor.GRAY + "Set the max. health for all players!", 
									ChatColor.GRAY + "1 Heart = 2HP")), 
							Settings.isCustomHealth));
					break;
				case 6:
					gui.setItem(i, createItem(Material.GHAST_TEAR, LanguageMessages.guiSharedHealthName, 
							new ArrayList<String>(Arrays.asList(ChatColor.GRAY + "Health will be shared across all players!")), 
							Settings.isSharedHealth));
					break;
				case 7:
					gui.setItem(i, createItem(Material.GRASS_BLOCK, LanguageMessages.guiNoPlacingName, 
							Lists.newArrayList(ChatColor.GRAY + "Block placing is forbidden!", 
									ChatColor.GRAY + "Exception:", 
									ChatColor.GRAY + "Ender eyes", 
									ChatColor.GRAY + "Water/Lava bucket", 
									ChatColor.GRAY + "Flint and Steel",
									ChatColor.GRAY + "when lighting up a portal"), 
							Settings.noBlockPlace));
					break;
				case 8:
					gui.setItem(i, createItem(Material.STONE, LanguageMessages.guiNoBreakingName, 
							Lists.newArrayList(ChatColor.GRAY + "Block breaking is forbidden!"), Settings.noBlockBreaking));
					break;
				case 9:
					gui.setItem(i, createItem(Material.CRAFTING_TABLE, LanguageMessages.guiNoCraftingName, 
							Lists.newArrayList(ChatColor.GRAY + "Crafting is forbidden"), 
							Settings.noCrafting));
					break;
				case 10:
					gui.setItem(i, createItem(Material.STONE_PRESSURE_PLATE, LanguageMessages.guiNoSneakingName, 
							Lists.newArrayList(ChatColor.GRAY + "Sneaking is forbidden"), Settings.noSneaking));
					break;
				case 11:
					gui.setItem(i, createItem(Material.WHITE_GLAZED_TERRACOTTA, 
							LanguageMessages.guiRandomBlockDropsName, 
							Lists.newArrayList(ChatColor.GRAY + "Every block type will", ChatColor.GRAY + "drop another block type!"), 
							Settings.isRandomizedBlockDrops));
					break;
				case 12:
					gui.setItem(i, createItem(Material.ORANGE_GLAZED_TERRACOTTA, 
							LanguageMessages.guiRandomMobDropsName, 
							Lists.newArrayList(ChatColor.GRAY + "Every mob type will", ChatColor.GRAY + "drop another item type!"),  
							Settings.isRandomizedMobDrops));
					break;
				case 13:
					gui.setItem(i, createItem(Material.PURPLE_GLAZED_TERRACOTTA, 
							LanguageMessages.guiRandomCraftingName, 
							Lists.newArrayList(ChatColor.GRAY + "Every crafting recipe", ChatColor.GRAY + "will be randomized!",
									ChatColor.GRAY + "This also affects furnaces!"),
							Settings.isRandomizedCrafting));
					break;
				default:
					gui.setItem(i, createGlass());
				}
			}
		}
		else if(type == BACKPACK) {
			gui = plugin.getServer().createInventory(null, BackPack.BACKPACK_SIZE, ChatColor.GREEN + "Backpack");
			gui.setContents(BackPack.getContents());
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
			lore.add(ChatColor.GRAY + "Max HP: " + ChatColor.GREEN + Settings.customHP);
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
		meta.setDisplayName(ChatColor.GRAY + "TBA");
		item.setItemMeta(meta);
		return item;
	}
}
