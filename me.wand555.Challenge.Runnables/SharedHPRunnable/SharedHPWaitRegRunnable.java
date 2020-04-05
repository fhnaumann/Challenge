package SharedHPRunnable;

import org.bukkit.scheduler.BukkitRunnable;


import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.ChallengeData.ChallengeProfile;

public class SharedHPWaitRegRunnable extends BukkitRunnable {

	public SharedHPWaitRegRunnable(Challenge plugin) {
		this.runTaskLater(plugin, 10L);
	}
	
	@Override
	public void run() {
		ChallengeProfile.setSharedHPWaitRegRunnableID(0);
	}
}
