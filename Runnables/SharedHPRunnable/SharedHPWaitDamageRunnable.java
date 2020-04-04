package SharedHPRunnable;

import org.bukkit.scheduler.BukkitRunnable;


import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.Challenge.ChallengeProfile;

public class SharedHPWaitDamageRunnable extends BukkitRunnable {

	/**
	 * Runs 0.5s later and sets the ID in ChallengeProfile back to 0, so when taking damage a new instance is created and run
	 * @param plugin
	 */
	public SharedHPWaitDamageRunnable(Challenge plugin) {
		this.runTaskLater(plugin, 10L);
	}
	
	@Override
	public void run() {
		ChallengeProfile.setSharedHPWaitDamageRunnableID(0);
	}

}
