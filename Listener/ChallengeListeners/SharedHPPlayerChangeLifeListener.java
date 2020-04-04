package ChallengeListeners;

import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import SharedHPRunnable.SharedHPWaitDamageRunnable;
import SharedHPRunnable.SharedHPWaitRegRunnable;
import me.wand555.Challenge.Challenge.Challenge;
import me.wand555.Challenge.Challenge.ChallengeProfile;
import me.wand555.Challenge.Challenge.Settings;

public class SharedHPPlayerChangeLifeListener implements Listener {
	
	private Challenge plugin;
	
	public SharedHPPlayerChangeLifeListener(Challenge plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onSharedHPPlayerDamageEvent(EntityDamageEvent event) {
		if(Settings.isSharedHealth) {
			if(Settings.canTakeEffect()) {
				if(event.getEntityType() == EntityType.PLAYER) {
					if(event.getCause() != DamageCause.CUSTOM) {
						event.setCancelled(true);
						if(ChallengeProfile.getSharedHPWaitDamageRunnableID() == 0) {
							
							SharedHPWaitDamageRunnable runnable = new SharedHPWaitDamageRunnable(plugin);
							ChallengeProfile.setSharedHPWaitDamageRunnableID(runnable.getTaskId());
							Settings.setSharedHP(Settings.sharedHP - event.getFinalDamage());
							if(Settings.sharedHP > 0) {
								ChallengeProfile.fromUUIDToPlayer().stream()
								.filter(p -> p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)
								.forEach(p -> {
									p.setHealth(Settings.sharedHP);
									p.playEffect(EntityEffect.HURT);
								});
							}
							else {
								ChallengeProfile.fromUUIDToPlayer().stream()
								.filter(p -> p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)
								.forEach(p -> p.setHealth(0));
							}
							
						}					
					}	
				}
			}
			else {
				event.setCancelled(true);
			}
		}		
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onSharedHPPlayerRegEvent(EntityRegainHealthEvent event) {
		if(Settings.isSharedHealth && !Settings.noReg && !Settings.noRegHard) {
			if(Settings.canTakeEffect()) {
				if(event.getEntityType() == EntityType.PLAYER) {
					if(event.getRegainReason() != RegainReason.CUSTOM) {
						event.setCancelled(true);
						if(ChallengeProfile.getSharedHPWaitRegRunnableID() == 0) {
							Player player = (Player) event.getEntity();
							
							SharedHPWaitRegRunnable runnable = new SharedHPWaitRegRunnable(plugin);
							ChallengeProfile.setSharedHPWaitRegRunnableID(runnable.getTaskId());
							Settings.setSharedHP(Settings.sharedHP + event.getAmount());
							if(Settings.sharedHP < Settings.customHP) {
								ChallengeProfile.fromUUIDToPlayer().stream()
								.filter(p -> p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)
								.forEach(p -> {
									p.setHealth(Settings.sharedHP);
									p.setAbsorptionAmount(player.getAbsorptionAmount());
								});
							}
							else {
								ChallengeProfile.fromUUIDToPlayer().stream()
								.filter(p -> p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)
								.forEach(p -> {
									p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
									p.setAbsorptionAmount(player.getAbsorptionAmount());
								});
							}
						}	
					}		
				}
			}
			else {
				event.setCancelled(true);
			}
		}	
	}

}
