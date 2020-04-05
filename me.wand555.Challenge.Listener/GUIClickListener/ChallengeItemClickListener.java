package GUIClickListener;

import org.apache.commons.lang.StringUtils;

import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import com.google.common.collect.Lists;

import GUI.GUI;
import GUIType.GUIType;
import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.ChallengeData.ChallengeProfile;
import me.wand555.Challenge.ChallengeData.Settings;
import me.wand555.Challenge.Util.SignMenuFactory;
import me.wand555.Challenge.WorldLinking.WorldLinkManager;

public class ChallengeItemClickListener implements Listener {

	private Challenge plugin;
	private GUI gui;
	private SignMenuFactory signMenuFactory;
	
	public ChallengeItemClickListener(Challenge plugin, GUI gui, SignMenuFactory signMenuFactory) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
		this.gui = gui;
		this.signMenuFactory = signMenuFactory;
		
	}
	
	@EventHandler
	public void onChallengeItemClickEvent(InventoryClickEvent event) {
		if(event.getClickedInventory() != null) {
			if(event.getCurrentItem() != null) {
				if(event.getWhoClicked() instanceof Player) {
					if(event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Settings")) {
						Player p = (Player) event.getWhoClicked();
						int slot = event.getRawSlot();
						if(slot <= 35) event.setCancelled(true);
						if(p.hasPermission("challenge.settings.change")) {
							switch(slot) {
							case 0:
								Settings.setEndOnDeath();
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								break;
							case 1:
								Settings.setSpawnNearFortress();
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								break;
							case 2:
								Settings.setNoDamage();
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								break;
							case 3:
								//change that settings before switching noReg, because gamerule logic is inverted to mine
								WorldLinkManager.worlds.stream().forEach(w -> w.setGameRule(GameRule.NATURAL_REGENERATION, Settings.noReg));
								Settings.setNoReg();
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								break;
							case 4:
								//change that settings before switching noReg, because gamerule logic is inverted to mine
								WorldLinkManager.worlds.stream().forEach(w -> w.setGameRule(GameRule.NATURAL_REGENERATION, Settings.noRegHard));
								
								Settings.setNoRegHard();
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								break;
							case 5:
								if(!Settings.isCustomHealth) {
									signMenuFactory
							            .newMenu(Lists.newArrayList("", "", "Enter HP", ""))
							            .reopenIfFail()
							            .response((player, lines) -> {
							                if(StringUtils.isNumeric(lines[0])) {
							                	int amount = Integer.valueOf(lines[0]);
							                	if(amount > 0) {
							                		player.sendMessage(Challenge.PREFIX + ChatColor.GRAY + "Changed settings");
							                		Settings.setCustomHP(amount);
							                		return true;
							                	}
							                	else {
							                		player.sendMessage(Challenge.PREFIX + ChatColor.GRAY + "'" + ChatColor.GREEN + 
							                				lines[0] + ChatColor.GRAY + "' has to be between 0 and MAX_LIFE");
							                	}
							                    return true;
							                }
							                else {
							                	player.sendMessage(Challenge.PREFIX + ChatColor.GRAY + "'" + ChatColor.GREEN + 
							                			lines[0] + ChatColor.GRAY + "' is not a number.");
							                }
							                return false; // failure. becaues reopenIfFail was called, menu will reopen when closed.
							            })
							            .open(p);
									Settings.setIsCustomHealth();
													
								}
								else {
									Settings.setIsCustomHealth();
									gui.createGUI(p, GUIType.OVERVIEW);		
									reloadOtherPlayerInvs(gui, p);
								}
								break;
							case 6:
								Settings.setIsSharedHealth();
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								break;
							case 7:
								Settings.setNoBlockPlace();
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								break;
							case 8:
								Settings.setNoBlockBreaking();
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								break;
							case 9:
								Settings.setNoCrafting();
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								break;
							case 10:
								Settings.setNoSneaking();
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
							case 11:
								Settings.setRandomizedBlockDrops();
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								break;
							case 12:
								Settings.setRandomizedMobDrops();
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								break;
							case 13:
								Settings.setRandomizedCrafting();
								gui.createGUI(p, GUIType.OVERVIEW);
								reloadOtherPlayerInvs(gui, p);
								break;
							default:
								break;
								
							}
						}
						else {
							//no change perm
						}
					}	
				}	
			}
		}
	}
	
	private void reloadOtherPlayerInvs(GUI gui, Player changer) {
		ChallengeProfile.fromUUIDToPlayer().stream()
			.filter(p -> !p.getUniqueId().equals(changer.getUniqueId()))
			.filter(p -> p.getOpenInventory().getType() != InventoryType.CRAFTING)
			.forEach(p -> gui.createGUI(p, GUIType.OVERVIEW));
	}
}
