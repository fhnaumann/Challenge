package StartRunnables;
import org.bukkit.ChatColor;

public enum TimerMessage {
START_TIMER(ChatColor.GRAY + "" + ChatColor.BOLD + "/timer start"),
TIMER_PAUSED(ChatColor.GRAY + "" + ChatColor.BOLD + "PAUSED" + ChatColor.DARK_GREEN +" [TIME] " + ChatColor.GRAY + "- /timer pause"),
TIMER_FINISHED(ChatColor.GRAY + "" + ChatColor.BOLD + "Final Time: " + ChatColor.DARK_GREEN + " [TIME] " + ChatColor.GRAY + " - /challenge reset or /challenge restore")
;

	private String msg;
	
	TimerMessage(String msg) {
		this.msg = msg;
	}
	
	public String getMessage() {
		return this.msg;
	}
}
