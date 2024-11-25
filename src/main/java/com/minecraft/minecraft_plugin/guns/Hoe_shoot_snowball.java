package com.minecraft.minecraft_plugin.guns;

import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class Hoe_shoot_snowball implements Listener {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private static final long SHOOT_COOLDOWN = 100;

    @EventHandler
    public void onShovelRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() == RIGHT_CLICK_AIR || event.getAction() == RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getType().toString().endsWith("_HOE") && canShoot(player)) {
                shootSnowball(player);
                setCooldown(player);
            }
        }
    }

    @EventHandler
    public void onSnowballHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();
            if ("CustomSnowball".equals(snowball.getCustomName()) && snowball.getShooter() instanceof Player) {
                if (event.getHitEntity() instanceof Player hitPlayer) {
                    hitPlayer.damage(2.0);
                }
            }
        }
    }
    private void shootSnowball(Player player) {
        Snowball snowball = player.launchProjectile(Snowball.class);
        snowball.setVelocity(player.getLocation().getDirection().multiply(1.5));
        snowball.setShooter(player);
        snowball.setCustomName("CustomSnowball");
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
