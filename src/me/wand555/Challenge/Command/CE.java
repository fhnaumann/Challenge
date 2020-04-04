package me.wand555.Challenge.Command;

import org.bukkit.WorldCreator;
import org.bukkit.attribute.Attribute;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import GUI.GUI;
import GUIType.GUIType;
import StartRunnables.SecondTimer;
import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.Challenge.ChallengeProfile;
import me.wand555.Challenge.Challenge.Position;
import me.wand555.Challenge.Challenge.PositionManager;
import me.wand555.Challenge.Challenge.Settings;
import me.wand555.Challenge.Challenge.WorldLinkManager;
import me.wand555.Challenge.Util.WorldUtil;

public class CE implements CommandExecutor {

	private Challenge plugin;
	private GUI gui;
	
	public CE(Challenge plugin, GUI gui) {
		this.plugin = plugin;
		this.gui = gui;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("challenge")) {
				//challenge create
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("join")) {
						if(!WorldLinkManager.worlds.isEmpty()) {
							if(!WorldLinkManager.worlds.contains(p.getWorld())) {
								WorldUtil.storePlayerInformationBeforeChallenge(p);
								//if(ChallengeProfile.isInChallenge(p.getUniqueId())) {
									//if player has joined, left and now joined again
								WorldUtil.loadPlayerInformationInChallengeAndApply(p);
								//}
								
								//can actually be transfered to changeworldlistener
								ChallengeProfile.addToParticipants(p.getUniqueId());
								System.out.println("Size: " + ChallengeProfile.getParticipants().size());
								//p.teleport(WorldLinkManager.worlds.stream().filter(w -> w.getEnvironment() == Environment.NORMAL).findFirst().get().getSpawnLocation(), TeleportCause.PLUGIN);
								p.sendMessage("Teleported.");
							}
							else {
								p.sendMessage("You're already in the challenge world.");
							}
						}
						else {
							p.sendMessage("There is no challenge to join.");
						}
					}
					else if(args[0].equalsIgnoreCase("leave")) {
						if(WorldLinkManager.worlds.contains(p.getWorld())) {
							WorldUtil.storePlayerInformationInChallenge(p);
							WorldUtil.loadPlayerInformationBeforeChallengeAndApply(p);
							ChallengeProfile.removeFromParticipants(p.getUniqueId());
							//p.teleport(WorldLinkManager.worlds.stream().filter(w -> w.getEnvironment() == Environment.NORMAL).findFirst().get().getSpawnLocation(), TeleportCause.PLUGIN);
							p.sendMessage("Teleported.");
						}
						else {
							p.sendMessage("You're not in a challenge.");
						}
					}
					else if(args[0].equalsIgnoreCase("restore")) {
						if(WorldLinkManager.worlds.contains(p.getWorld())) {
							//to ensure challenge was over before
							if(Settings.isDone) {
								ChallengeProfile.restoreChallenge();
							}
						}
					}
					else if(args[0].equalsIgnoreCase("reset")) {
						if(WorldLinkManager.worlds.contains(p.getWorld())) {
							//QUASI ALLES IN CHALLENGEPROFILE ZURÜCKSETZEN
							//logik: Alle player von der challenge in eine bestimmte (existierende) welt tpen
							//challenge welten löschen und neu erstellen -> alle tpen
							Settings.restoreDefault();				
							WorldUtil.deleteChallengeWorldsAndPlayerData();
							WorldUtil.deletePortalData();
							
							ChallengeProfile.onReset();
							WorldLinkManager.worlds.clear();
							
							p.sendMessage("Deleted challenge worlds.");
							p.sendMessage("Reload/Restart the server and type /challenge join to join a new challenge.");
						}
						else {
							p.sendMessage("You have to be in the challenge to reset it.");
						}
					}
					else {
						p.sendMessage("Syntax: /challenge <option>");
					}
				}
				else {
					p.sendMessage("Syntax: /challenge <option>");
				}
			}
			else if(cmd.getName().equalsIgnoreCase("timer")) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("start")) {
						if(WorldLinkManager.worlds.contains(p.getWorld())) { 
							if(!Settings.hasStarted) {
								ChallengeProfile.startTimer();
							}
							else {
								p.sendMessage("Timer is started. Use /timer pause to pause or resume the timer.");
							}						
						}
						else {
							p.sendMessage("You're not in a challenge world.");
						}
					}
					else if(args[0].equalsIgnoreCase("pause")) {
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
								p.sendMessage("Cannot pause. Challenge is not running.");
							}
						}
						else {
							p.sendMessage("You're not in a challenge world.");
						}
					}
					else {
						p.sendMessage("Syntax: /timer <option>");
					}
				}
				else {
					p.sendMessage("Syntax: /timer <option>");
				}
			}
			else if(cmd.getName().equalsIgnoreCase("hp")) {
				//to yourself
				if(args.length == 1) {
					if(StringUtils.isNumeric(args[0])) {
						int amount = Integer.valueOf(args[0]);
						if(amount >= 0 && amount < p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
							p.setHealth(amount);
							p.sendMessage("Set HP.");
						}
						else {
							p.sendMessage("Amount has to be 0 <= amount < MAX_HEALTH.");
						}
					}
					else {
						p.sendMessage("'" + args[1] + "' is not a number.");
					}
				}
				//to somebody else
				else if(args.length == 2) {
					if(StringUtils.isNumeric(args[1])) {
						int amount = Integer.valueOf(args[1]);
						if(amount >= 0 || amount < p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
							Player playerTo = Bukkit.getPlayer(args[0]);
							if(playerTo != null) {
								playerTo.setHealth(amount);
								p.sendMessage("Set HP.");
							}
							else {
								p.sendMessage("The player '" + args[0] + "' is not online.");
							}
							
						}
						else {
							p.sendMessage("Amount has to be 0 <= amount < MAX_HEALTH.");
						}
					}
					else {
						p.sendMessage("'" + args[1] + "' is not a number.");
					}
				}
				else {
					p.sendMessage("Syntax: /hp <player> <amount>");
				}
			}
			else if(cmd.getName().equalsIgnoreCase("pos")) {
				if(args.length == 0) {
					PositionManager.positions.forEach(pos -> {
						p.sendMessage(PositionManager.displayPosition(pos));
					});
				}
				else if(args.length == 1) {
					//if contains...
					if(PositionManager.positionWithNameExists(args[0])) {
						p.sendMessage(PositionManager.displayPosition(PositionManager.getPositionFromName(args[0])));
					}
					else {
						PositionManager.addToPosition(new Position(args[0], p.getLocation(), p.getUniqueId(), new Date()));
						p.sendMessage("Registered position " + args[0]);
					}
					
				}
				else {
					p.sendMessage("Syntax: /pos <name>");
				}
			}
			else if(cmd.getName().equalsIgnoreCase("settings")) {
				if(args.length == 0) {
					if(WorldLinkManager.worlds.contains(p.getWorld())) {
						if(Settings.isPaused || !Settings.hasStarted) {
							gui.createGUI(p, GUIType.OVERVIEW);
						}
						else {
							p.sendMessage("Timer has to be paused. /timer pause");
						}				
					}
					else {
						p.sendMessage("You're not in a challenge world.");
					}
				}
				else {
					p.sendMessage("Syntax: /settings");
				}
			}
		}
		else {
			sender.sendMessage("Only for players");
		}
		return true;
	}

}
