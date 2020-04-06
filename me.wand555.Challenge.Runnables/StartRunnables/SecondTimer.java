package StartRunnables;


import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.Config.Language.LanguageMessages;
import me.wand555.Challenge.Util.DateUtil;
import me.wand555.Challenge.WorldLinking.WorldLinkManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class SecondTimer extends BukkitRunnable {
	private TimerMessage messageType;
	private long time;
	private boolean increaseTime;
	
	public SecondTimer(Challenge plugin, long time) {
		Bukkit.getLogger().log(Level.WARNING, "Instance Created");
		this.time = time;
		this.increaseTime = true;
		this.runTaskTimer(plugin, 0, 20L);
	}
	
	public SecondTimer(Challenge plugin, TimerMessage messageType) {
		this.messageType = messageType;
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
			TextComponent component = new TextComponent(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + displayTime);
			Bukkit.getOnlinePlayers().stream()
			.filter(p -> WorldLinkManager.worlds.contains(p.getWorld()))
			.forEach(p -> p.spigot().sendMessage(ChatMessageType.ACTION_BAR, component)); 
		}
		else {
			switch(messageType) {
			case START_TIMER:
				TextComponent component = new TextComponent(LanguageMessages.timerMessageStart);	
				Bukkit.getOnlinePlayers().stream()
				.filter(p -> WorldLinkManager.worlds.contains(p.getWorld()))
				.forEach(p -> p.spigot().sendMessage(ChatMessageType.ACTION_BAR, component));
				break;
			case TIMER_PAUSED:
				TextComponent component1 = new TextComponent(LanguageMessages.timerMessagePause
						.replace("[TIME]", DateUtil.formatDuration(getTime())));
				Bukkit.getOnlinePlayers().stream()
				.filter(p -> WorldLinkManager.worlds.contains(p.getWorld()))
				.forEach(p -> p.spigot().sendMessage(ChatMessageType.ACTION_BAR, component1));
				break;
			case TIMER_FINISHED:
				TextComponent component2 = new TextComponent(LanguageMessages.timerMessageFinished
						.replace("[TIME]", DateUtil.formatDuration(getTime())));
				Bukkit.getOnlinePlayers().stream()
				.filter(p -> WorldLinkManager.worlds.contains(p.getWorld()))
				.forEach(p -> p.spigot().sendMessage(ChatMessageType.ACTION_BAR, component2));
				break;
			default:
				break;
			
			}
			
			
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
	
	public TimerMessage getMessageType() {
		return this.messageType;
	}
	
	public void setMessageType(TimerMessage messageType) {
		this.messageType = messageType;
	}
}
