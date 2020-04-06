package me.wand555.Challenge.Command;

import org.bukkit.attribute.Attribute;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import GUI.GUI;
import GUIType.GUIType;
import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.ChallengeData.ChallengeProfile;
import me.wand555.Challenge.ChallengeData.Settings;
import me.wand555.Challenge.ChallengeData.Position.Position;
import me.wand555.Challenge.ChallengeData.Position.PositionManager;
import me.wand555.Challenge.Config.Language.Language;
import me.wand555.Challenge.Config.Language.LanguageMessages;
import me.wand555.Challenge.Util.WorldUtil;
import me.wand555.Challenge.WorldLinking.WorldLinkManager;

public class CE implements CommandExecutor {

	private Challenge plugin;
	private GUI gui;
	
	public CE(Challenge plugin, GUI gui) {
		this.plugin = plugin;
		this.gui = gui;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("challenge")) {
				//challenge create
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("join")) {
						if(p.hasPermission("challenge.join")) {
							if(!WorldLinkManager.worlds.isEmpty()) {
								if(!WorldLinkManager.worlds.contains(p.getWorld())) {
									WorldUtil.storePlayerInformationBeforeChallenge(p);
									//if(ChallengeProfile.isInChallenge(p.getUniqueId())) {
										//if player has joined, left and now joined again
									WorldUtil.loadPlayerInformationInChallengeAndApply(p);
									//}
									
									//can actually be transfered to changeworldlistener
									ChallengeProfile.addToParticipants(p.getUniqueId());
									//p.teleport(WorldLinkManager.worlds.stream().filter(w -> w.getEnvironment() == Environment.NORMAL).findFirst().get().getSpawnLocation(), TeleportCause.PLUGIN);
									p.sendMessage(LanguageMessages.teleportMsg);
								}
								else {
									p.sendMessage(LanguageMessages.alreadyInChallenge);
								}
							}
							else {
								p.sendMessage(Challenge.PREFIX + ChatColor.GRAY + "There is no challenge to join.");
							}
						}
						else {
							//no perm
						}
					}
					else if(args[0].equalsIgnoreCase("leave")) {
						if(p.hasPermission("challenge.leave")) {
							if(WorldLinkManager.worlds.contains(p.getWorld())) {
								WorldUtil.storePlayerInformationInChallenge(p);
								WorldUtil.loadPlayerInformationBeforeChallengeAndApply(p);
								ChallengeProfile.removeFromParticipants(p.getUniqueId());
								//p.teleport(WorldLinkManager.worlds.stream().filter(w -> w.getEnvironment() == Environment.NORMAL).findFirst().get().getSpawnLocation(), TeleportCause.PLUGIN);
								p.sendMessage(LanguageMessages.teleportMsg);
							}
							else {
								p.sendMessage(LanguageMessages.notInChallenge);
							}
						}
						else {
							//no perm
						}
					}
					else if(args[0].equalsIgnoreCase("restore")) {
						if(p.hasPermission("challenge.restore")) {
							if(WorldLinkManager.worlds.contains(p.getWorld())) {
								//to ensure challenge was over before
								if(Settings.isDone) {
									ChallengeProfile.restoreChallenge();
								}
							}
						}
						else {
							//no perm
						}
					}
					else if(args[0].equalsIgnoreCase("reset")) {
						if(p.hasPermission("challenge.reset")) {
							if(WorldLinkManager.worlds.contains(p.getWorld())) {
								//QUASI ALLES IN CHALLENGEPROFILE ZURÜCKSETZEN
								//logik: Alle player von der challenge in eine bestimmte (existierende) welt tpen
								//challenge welten löschen und neu erstellen -> alle tpen
								Settings.restoreDefault();				
								WorldUtil.deleteChallengeWorldsAndPlayerData();
								WorldUtil.deletePortalData();
								
								ChallengeProfile.onReset();
								WorldLinkManager.worlds.clear();
								
								p.sendMessage(LanguageMessages.deletedChallengeWorlds);
								p.sendMessage(LanguageMessages.resetWarning);
							}
							else {
								p.sendMessage(LanguageMessages.notInChallenge);
							}
						}
						else {
							//no perm
						}
					}
					else {
						p.sendMessage(LanguageMessages.challengeOptionSyntax);
					}
				}
				else {
					p.sendMessage(Challenge.PREFIX + ChatColor.GRAY + "Syntax: /challenge <option>");
				}
			}
			else if(cmd.getName().equalsIgnoreCase("timer")) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("start")) {
						if(p.hasPermission("challenge.timer.start")) {
							if(WorldLinkManager.worlds.contains(p.getWorld())) { 
								if(!Settings.hasStarted) {
									ChallengeProfile.startTimer();
								}
								else {
									p.sendMessage(LanguageMessages.timerAlreadyStarted);
								}						
							}
							else {
								p.sendMessage(LanguageMessages.notInChallenge);
							}
						}
						else {
							//no perm
						}
					}
					else if(args[0].equalsIgnoreCase("pause")) {
						if(p.hasPermission("challenge.timer.pause")) {
							if(WorldLinkManager.worlds.contains(p.getWorld())) { 
								//when timer is normal running
								if(Settings.canTakeEffect()) {
									ChallengeProfile.pauseTimer();
								}
								//"running" but timer is paused right now
								else if(Settings.isPaused){
									ChallengeProfile.resumeTimer();
								}
								else {
									p.sendMessage(LanguageMessages.noPauseBecauseNotRunning);
								}
							}
							else {
								p.sendMessage(LanguageMessages.notInChallenge);
							}
						}
						else {
							//no perm
						}
					}
					else {
						p.sendMessage(LanguageMessages.timerOptionSyntax);
					}
				}
				else {
					p.sendMessage(LanguageMessages.timerOptionSyntax);
				}
			}
			else if(cmd.getName().equalsIgnoreCase("hp")) {
				//to yourself
				if(args.length == 1) {
					if(p.hasPermission("challenge.hp")) {
						if(StringUtils.isNumeric(args[0])) {
							int amount = Integer.valueOf(args[0]);
							if(amount >= 0 && amount <= p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
								p.setHealth(amount);
								p.sendMessage(LanguageMessages.setHP);
							}
							else {
								p.sendMessage(LanguageMessages.setHPOutOfRange);
							}
						}
						else {
							p.sendMessage(LanguageMessages.notANumber.replace("[NUMBER]", args[0]));
						}
					}
					else {
						//no perm
					}
				}
				//to somebody else
				else if(args.length == 2) {
					if(p.hasPermission("challenge.hp")) {
						if(StringUtils.isNumeric(args[1])) {
							int amount = Integer.valueOf(args[1]);
							if(amount >= 0 || amount < p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
								Player playerTo = Bukkit.getPlayer(args[0]);
								if(playerTo != null) {
									playerTo.setHealth(amount);
									p.sendMessage(LanguageMessages.setHP);
								}
								else {
									p.sendMessage(LanguageMessages.playerNotOnline.replace("[PLAYER]", Bukkit.getOfflinePlayer(args[0]).getName()));
								}
								
							}
							else {
								p.sendMessage(LanguageMessages.setHPOutOfRange);
							}
						}
						else {
							p.sendMessage(LanguageMessages.notANumber.replace("[NUMBER]", args[0]));
						}
					}
					else {
						//no perm
					}
				}
				else {
					p.sendMessage(LanguageMessages.hpOptionSyntax);
				}
			}
			else if(cmd.getName().equalsIgnoreCase("pos")) {
				if(args.length == 0) {
					if(p.hasPermission("challenge.pos.list")) {
						PositionManager.positions.forEach(pos -> {
							p.sendMessage(PositionManager.displayPosition(pos));
						});
					}
					else {
						//no perm
					}
				}
				else if(args.length == 1) {
					if(p.hasPermission("challenge.pos.add")) {
						//if contains...
						if(PositionManager.positionWithNameExists(args[0])) {
							p.sendMessage(Challenge.PREFIX + ChatColor.GRAY + PositionManager.displayPosition(PositionManager.getPositionFromName(args[0])));
						}
						else {
							PositionManager.addToPosition(new Position(args[0], p.getLocation(), p.getUniqueId(), new Date()));
							p.sendMessage(LanguageMessages.registeredPosition.replace("[POS]", args[0]));
						}		
					}
					else {
						//no perm
					}
				}
				else {
					p.sendMessage(LanguageMessages.positionSyntax);
				}
			}
			else if(cmd.getName().equalsIgnoreCase("bp")) {
				if(args.length == 0) {
					if(p.hasPermission("challenge.bp")) {
						if(WorldLinkManager.worlds.contains(p.getWorld())) {
							gui.createGUI(p, GUIType.BACKPACK);
						}
						else {
							p.sendMessage(LanguageMessages.notInChallenge);
						}
					}
					else {
						//no perm
					}
				}
				else {
					p.sendMessage(LanguageMessages.bpSyntax);
				}
			}
			else if(cmd.getName().equalsIgnoreCase("settings")) {
				if(args.length == 0) {
					if(p.hasPermission("challenge.settings.view")) {
						if(WorldLinkManager.worlds.contains(p.getWorld())) {
							if(Settings.isPaused || !Settings.hasStarted) {
								gui.createGUI(p, GUIType.OVERVIEW);
							}
							else {
								p.sendMessage(LanguageMessages.noSettingsHasToBePaused);
							}				
						}
						else {
							p.sendMessage(LanguageMessages.notInChallenge);
						}
					}
					else {
						//no perm
					}
				}
				else {
					p.sendMessage(LanguageMessages.settingSyntax);
				}
			}
		}
		else {
			sender.sendMessage("Only for players!");
		}
		return true;
	}

}
