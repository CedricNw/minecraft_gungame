package com.minecraft.minecraft_plugin.guns;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class Shovel_shoot_rocket extends Weapon implements Listener {

    public Shovel_shoot_rocket() {
        super(4500, 1, 4500, 0, Material.FIRE_CHARGE);
    }

    @EventHandler
    public void onShovelRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() == RIGHT_CLICK_AIR || event.getAction() == RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getType().toString().endsWith("_SHOVEL") && canShoot(player)) {
                shootProjectile(player, 0.2, SmallFireball.class);
                setCooldown(player);
            }
        }
    }

    @EventHandler
    public void onFirechargeHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof SmallFireball) {
            SmallFireball fireball = (SmallFireball) event.getEntity();
            if ("CustomBullet".equals(fireball.getCustomName())) {
                fireball.getWorld().createExplosion(fireball.getLocation(), 3.0f, false, false);
                fireball.remove();
            }
        }
    }

    @Override
    public void shootProjectile(Player player, double travelspeed, Class projectile) {
        SmallFireball fireball = player.launchProjectile(SmallFireball.class);
        Vector direction = player.getLocation().getDirection().multiply(travelspeed);
        fireball.setVelocity(direction);
        fireball.setShooter(player);
        fireball.setIsIncendiary(false);
        fireball.setCustomName("CustomBullet");
        shotsFired += 1;
        removeBullet(player);
        magazineNotEmpty(player);
    }
}
