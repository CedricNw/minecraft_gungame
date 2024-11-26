package com.minecraft.minecraft_plugin.other;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class Granade implements Listener {

    private final Plugin plugin;

    public Granade(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEggHit(ProjectileHitEvent event) {
        if (event.getEntity().getType() != EntityType.EGG) {
            return;
        }

        Egg egg = (Egg) event.getEntity();
        Player thrower = (Player) egg.getShooter();

        if (thrower == null) {
            return;
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            var hitLocation = egg.getLocation();
            hitLocation.getWorld().createExplosion(egg.getLocation(), 5.0f, false, false);
            hitLocation.getWorld().playSound(hitLocation, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);

            if (event.getHitEntity() instanceof Player hitPlayer) {
                Vector knockback = hitPlayer.getLocation().toVector().subtract(hitLocation.toVector()).normalize().multiply(2);
                hitPlayer.setVelocity(knockback);
            }
        }, 20L);
    }
}