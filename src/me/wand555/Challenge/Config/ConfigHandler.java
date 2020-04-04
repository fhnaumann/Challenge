package me.wand555.Challenge.Config;

import java.io.File;


import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.configuration.file.YamlConfiguration;

import StartRunnables.SecondTimer;
import StartRunnables.TimerMessage;

import org.bukkit.Axis;
import org.bukkit.World.Environment;
import org.bukkit.configuration.file.FileConfiguration;

import me.wand555.Challenge.Challenge.ChallengeProfile;
import me.wand555.Challenge.Challenge.Position;
import me.wand555.Challenge.Challenge.PositionManager;
import me.wand555.Challenge.Challenge.EndLinking.ObsidianPlatform;
import me.wand555.Challenge.NetherLinking.Gate;
import me.wand555.Challenge.Util.DateUtil;
import static me.wand555.Challenge.Challenge.Settings.*;

public class ConfigHandler extends ConfigUtil {

	public static void storeToConfig() {
		storeNetherPortalToConfig();
		storeEndPortalToConfig();
		storeChallengeProfilesAndTimers();
		storePositionsToConfig();
	}
	
	public static void loadFromConfig() {
		loadNetherPortalFromConfig();
		loadEndPortalFromConfig();
		loadChallengeProfilesAndTimers();
		loadPositionsFromConfig();
	}
	
	private static void loadPositionsFromConfig() {
		checkOrdner();
		File file = new File(PLUGIN.getDataFolder()+"", "positions.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		for(String name : cfg.getKeys(false)) {
			PositionManager.addToPosition(new Position(
					name, 
					deserializeLocation(cfg.getString(name+".Location")), 
					UUID.fromString(cfg.getString(name+".Creator").trim()), 
					cfg.getString(name+".Date")));
		}
	}
	
	private static void storePositionsToConfig() {
		clearFile("positions.yml");
		File file = new File(PLUGIN.getDataFolder()+"", "positions.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		for(Position pos : PositionManager.positions) {
			cfg.set(pos.getName()+".Location", serializeLocation(pos.getLocation()));
			cfg.set(pos.getName()+".Creator", pos.getCreator().toString());
			cfg.set(pos.getName()+".Date", pos.getDate());
		}
		saveCustomYml(cfg, file);
	}
	
	private static void loadChallengeProfilesAndTimers() {
		checkOrdner();
		File file = new File(PLUGIN.getDataFolder()+"", "profilesAndTimers.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		ChallengeProfile.setParticipants(cfg.getStringList("Participants").stream()
				.map(s -> UUID.fromString(s.trim()))
				.collect(Collectors.toCollection(HashSet::new)));
		//has to be done this way, because I need a new instance, but the timer shouldnt continue.
		if(cfg.isSet("hasStarted")) {
			hasStarted = cfg.getBoolean("hasStarted");
			isPaused = hasStarted ? true : false;
			endOnDeath = cfg.getBoolean("endOnDeath");
			spawnNearFortress = cfg.getBoolean("spawnNearFortress");
			noDamage = cfg.getBoolean("noDamage");
			noReg = cfg.getBoolean("noReg");
			noRegHard = cfg.getBoolean("noRegHard");
			isCustomHealth = cfg.getBoolean("isCustomHealth");
			customHP = cfg.getInt("customHP");
			isSharedHealth = cfg.getBoolean("isSharedHealth");
			sharedHP = cfg.getInt("sharedHP");
			noBlockPlace = cfg.getBoolean("noBlockPlace");
			noBlockBreaking = cfg.getBoolean("noBlockBreaking");
			noCrafting = cfg.getBoolean("noCrafting");
			noSneaking = cfg.getBoolean("noSneaking");			
		}	
		
		if(hasStarted) {
			ChallengeProfile.setSecondTimer(new SecondTimer(PLUGIN, TimerMessage.TIMER_PAUSED.getMessage().replace("[TIME]", DateUtil.formatDuration(cfg.getLong("Timer")))));
			ChallengeProfile.getSecondTimer().setTime(cfg.getLong("Timer"));
		}
		else {
			ChallengeProfile.setSecondTimer(new SecondTimer(PLUGIN, TimerMessage.START_TIMER.getMessage()));
		}
	}
	
	private static void storeChallengeProfilesAndTimers() {
		clearFile("profilesAndTimers.yml");
		File file = new File(PLUGIN.getDataFolder()+"", "profilesAndTimers.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set("Participants", ChallengeProfile.getParticipants().stream().map(uuid -> uuid.toString()).collect(Collectors.toList())); //maybe convert to List
		cfg.set("hasStarted", hasStarted);
		cfg.set("endOnDeath", endOnDeath);
		cfg.set("spawnNearFortress", spawnNearFortress);
		cfg.set("noDamage", noDamage);
		cfg.set("noReg", noReg);
		cfg.set("noRegHard", noRegHard);
		cfg.set("isCustomHealth", isCustomHealth);
		cfg.set("customHP", customHP);
		cfg.set("isSharedHealth", isSharedHealth);
		cfg.set("sharedHP", sharedHP);
		cfg.set("noBlockPlace", noBlockPlace);
		cfg.set("noBlockBreaking", noBlockBreaking);
		cfg.set("noCrafting", noCrafting);
		cfg.set("noSneaking", noSneaking);
		
		if(hasStarted) {
			cfg.set("Timer", ChallengeProfile.getSecondTimer().getTime());
		}
		saveCustomYml(cfg, file);
	}
	
	private static void loadEndPortalFromConfig() {
		checkOrdner();
		File file = new File(PLUGIN.getDataFolder()+"", "endportals.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		ObsidianPlatform.setCreated(cfg.getBoolean("Created"));
		if(ObsidianPlatform.isCreated()) {
			new ObsidianPlatform(deserializeLocation(cfg.getString("TeleportTo")));
		}
	}
	
	private static void storeEndPortalToConfig() {
		clearFile("endportals.yml");
		File file = new File(PLUGIN.getDataFolder()+"", "endportals.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set("Created", ObsidianPlatform.isCreated());
		if(ObsidianPlatform.isCreated()) {
			cfg.set("TeleportTo", serializeLocation(ObsidianPlatform.getPlatform().getTeleportTo()));
		}
		else {
			
		}
		
		saveCustomYml(cfg, file);
	}
	
	private static void loadNetherPortalFromConfig() {
		checkOrdner();
		File file = new File(PLUGIN.getDataFolder()+"", "netherportals.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		for(String key : cfg.getKeys(false)) {
			new Gate(cfg.getStringList(key+".FrameBlockLocs").stream().map(sLoc -> deserializeLocation(sLoc).getBlock()).collect(Collectors.toCollection(HashSet::new)), 
					cfg.getStringList(key+".PortalBlockLocs").stream().map(sLoc -> deserializeLocation(sLoc).getBlock()).collect(Collectors.toCollection(HashSet::new)), 
					Axis.valueOf(cfg.getString(key+".Axis")), 
					Environment.valueOf(cfg.getString(key+".Environment")), 
					deserializeLocationDetailed(key));
		}
	}
	
	private static void storeNetherPortalToConfig() {
		clearFile("netherportals.yml");
		File file = new File(PLUGIN.getDataFolder()+"", "netherportals.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);	
		for(Gate gate : Gate.getGates()) {
			String key = serializeLocationDetailed(gate.getTeleportTo()).replace('.', ',');
			cfg.set(key+".FrameBlockLocs", gate.getFrameBlocks().stream()
					.map(b -> serializeLocation(b.getLocation())).collect(Collectors.toList()));
			cfg.set(key+".PortalBlockLocs", gate.getPortalBlocks().stream()
					.map(b -> serializeLocation(b.getLocation())).collect(Collectors.toList()));
			cfg.set(key+".Axis", gate.getAxis().toString());
			cfg.set(key+".Environment", gate.getEnvironment().toString());
		}
		Gate.getGates().clear();
		saveCustomYml(cfg, file);
	}
}
 