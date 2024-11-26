package com.minecraft.minecraft_plugin.guns;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;


import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class Axe_shoot_handgun extends Weapon implements Listener {

    public Axe_shoot_handgun() {
        super(250, 15, 3000, 3.5, Material.SPECTRAL_ARROW);
    }

    @EventHandler
    public void onAxeRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() == RIGHT_CLICK_AIR || event.getAction() == RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getType().toString().endsWith("_AXE")
                    && canShoot(player))  {
                shootProjectile(player, 3.0, Snowball.class);
                setCooldown(player);
            }
        }
    }
    @Override
    public void shootProjectile(Player player, double travelspeed, Class projectile) {
        Snowball snowball = player.launchProjectile(Snowball.class);
        Vector direction = player.getLocation().getDirection().multiply(travelspeed);
        snowball.setVelocity(direction);
        snowball.setGravity(false);
        snowball.setShooter(player);
        snowball.setCustomName("CustomBullet");
        super.shotsFired += 1;
        removeBullet(player);
        magazineNotEmpty(player);
    }
}
