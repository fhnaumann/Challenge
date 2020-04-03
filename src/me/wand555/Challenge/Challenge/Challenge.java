package me.wand555.Challenge.Challenge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import ChallengeListeners.NoBlockBreakingListener;
import ChallengeListeners.NoBlockPlaceListener;
import ChallengeListeners.NoCraftListener;
import ChallengeListeners.NoDamageListener;
import ChallengeListeners.NoRegenListener;
import ChallengeListeners.NoSneakingListener;
import ChallengeListeners.PlayerDeathListener;
import ChallengeListeners.SharedHPPlayerChangeLifeListener;
import ChallengeProfileListener.PlayerJoinListener;
import ChallengeProfileListener.PlayerQuitListener;
import EnderDragonListener.EnderDragonDeathListener;
import GUI.GUI;
import GUIClickListener.ChallengeItemClickListener;
import StartListeners.PlayerTeleportWorldListener;
import StartRunnables.SecondTimer;
import me.wand555.Challenge.Command.CE;
import me.wand555.Challenge.Config.ConfigHandler;
import me.wand555.Challenge.NetherLinking.PortalListener;
import me.wand555.Challenge.Util.SignMenuFactory;

public class Challenge extends JavaPlugin {

	private Challenge plugin;
	private CE myCE;
	private GUI gui;
	private SignMenuFactory signMenuFactory;
	public static Random random = new Random();
	
	public void onEnable() {
		plugin = this;
		
		WorldLinkManager.worlds.add(Bukkit.createWorld(new WorldCreator("ChallengeOverworld").environment(Environment.NORMAL)));
		WorldLinkManager.worlds.add(Bukkit.createWorld(new WorldCreator("ChallengeNether").environment(Environment.NETHER)));
		WorldLinkManager.worlds.add(Bukkit.createWorld(new WorldCreator("ChallengeEnd").environment(Environment.THE_END)));
		
		this.gui = new GUI(this);
		this.signMenuFactory = new SignMenuFactory(this);
		
		ConfigHandler.loadFromConfig();
		
		
		
		registerListener();
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				//Bukkit.getOnlinePlayers().forEach(p -> System.out.println(p.getWorld().getName()));
			}
		}.runTaskTimer(this, 20L, 40L);
		
		myCE = new CE(this, gui);
		this.getCommand("challenge").setExecutor(myCE);
		this.getCommand("timer").setExecutor(myCE);
		this.getCommand("hp").setExecutor(myCE);
		this.getCommand("settings").setExecutor(myCE);
		
		if(Settings.hasStarted) {
			//ChallengeProfile.resumeTimer();
		}
	}
	
	public void onDisable() {
		System.out.println(Settings.hasStarted);
		if(!Settings.isPaused && Settings.hasStarted) {
			ChallengeProfile.pauseTimerOnDisable();
		}
		ConfigHandler.storeToConfig();
	}
	
	private void registerListener() {
		new PlayerTeleportWorldListener(this);
		new EnderDragonDeathListener(this);
		
		new ChallengeItemClickListener(this, gui, signMenuFactory);
		new PlayerDeathListener(this);
		new NoDamageListener(this);
		new NoRegenListener(this);
		new SharedHPPlayerChangeLifeListener(this);
		new NoBlockPlaceListener(this);
		new NoBlockBreakingListener(this);
		new NoCraftListener(this);
		new NoSneakingListener(this);
		
		new PortalListener(this);
		
		new PlayerJoinListener(this);
		new PlayerQuitListener(this);
	}
	
}
