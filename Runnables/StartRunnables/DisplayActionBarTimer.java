package StartRunnables;

import java.util.HashSet;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.wand555.Challenge.Challenge.Challenge;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class DisplayActionBarTimer extends BukkitRunnable {

	private HashSet<Player> players = new HashSet<Player>();
	private String msg;
	
	public DisplayActionBarTimer(Challenge plugin, HashSet<Player> players, String msg) {
		this.players = players;
		this.msg = msg;
		this.runTaskTimer(plugin, 0L, 20L);
	}
	
	@Override
	public void run() {
		players.stream().filter(Player::isOnline).forEach(p -> p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg)));		
	}
	
	public void setMessage(String message) {
		this.msg = message;
	}
	
	public void addToPlayers(Player p) {
		players.add(p);
	}

}
