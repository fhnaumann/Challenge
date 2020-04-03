package StartRunnables;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.Challenge.WorldLinkManager;
import me.wand555.Challenge.Util.DateUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class SecondTimer extends BukkitRunnable {
	private String msg;
	private long time;
	private boolean increaseTime;
	
	public SecondTimer(Challenge plugin, long time) {
		Bukkit.getLogger().log(Level.WARNING, "Instance Created");
		this.time = time;
		this.increaseTime = true;
		this.runTaskTimer(plugin, 0, 20L);
	}
	
	public SecondTimer(Challenge plugin, String msg) {
		this.msg = msg;
		this.increaseTime = false;
		this.runTaskTimer(plugin, 0, 20L);
	}
	
	public SecondTimer(long time) {
		this.time = time;
	}
	
	@Override
	public void run() {
		if(increaseTime) {
			this.time += 1;
			String displayTime = DateUtil.formatDuration(time);
			TextComponent component = new TextComponent(ChatColor.GOLD + displayTime);
			Bukkit.getOnlinePlayers().stream()
			.filter(p -> WorldLinkManager.worlds.contains(p.getWorld()))
			.forEach(p -> p.spigot().sendMessage(ChatMessageType.ACTION_BAR, component)); 
		}
		else {
			Bukkit.getOnlinePlayers().stream()
			.filter(p -> WorldLinkManager.worlds.contains(p.getWorld()))
			.forEach(p -> p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg)));
		}				
	}

	public long getTime() {
		return this.time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public void startIncreasing() {
		this.increaseTime = true;
	}
	
	public void stopIncreasing() {
		this.increaseTime = false;
	}
	
	public String getMessage() {
		return this.msg;
	}
	
	public void setMessage(String message) {
		this.msg = message;
	}
}
