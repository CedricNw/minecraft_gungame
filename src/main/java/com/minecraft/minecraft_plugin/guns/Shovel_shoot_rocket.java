package com.minecraft.minecraft_plugin.guns;

import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class Shovel_shoot_rocket implements Listener {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private static final long SHOOT_COOLDOWN = 4500;

    @EventHandler
    public void onShovelRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() == RIGHT_CLICK_AIR || event.getAction() == RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getType().toString().endsWith("_SHOVEL") && canShoot(player)) {
                shootFireball(player);
                setCooldown(player);
            }
        }
    }

    @EventHandler
    public void onFirechargeHit(ProjectileHitEvent event) {

        if (event.getEntity() instanceof SmallFireball) {
            SmallFireball fireball = (SmallFireball) event.getEntity();
            if ("ExplosiveFirecharge".equals(fireball.getCustomName())) {
                fireball.getWorld().createExplosion(fireball.getLocation(), 2.0f, false, false);
                fireball.remove();
            }
        }
    }

    private void shootFireball(Player player) {
        SmallFireball fireball = player.launchProjectile(SmallFireball.class);
        Vector direction = player.getLocation().getDirection().multiply(0.2);
        fireball.setVelocity(direction);
        fireball.setShooter(player);
        fireball.setIsIncendiary(false);
        fireball.setCustomName("ExplosiveFirecharge");
    }

    private boolean canShoot(Player player) {
        UUID playerId = player.getUniqueId();
        if (!cooldowns.containsKey(playerId)) {
            return true;
        }

        long lastShotTime = cooldowns.get(playerId);
        return (System.currentTimeMillis() - lastShotTime) >= SHOOT_COOLDOWN;
    }

    private void setCooldown(Player player) {
        UUID playerId = player.getUniqueId();
        cooldowns.put(playerId, System.currentTimeMillis());
    }
}
