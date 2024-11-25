package com.minecraft.minecraft_plugin.guns;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class Hoe_shoot_snowball extends Weapon implements Listener {

    public Hoe_shoot_snowball() {
        super(0, 30, 2500, 2.0, Material.SNOWBALL);
    }

    @EventHandler
    public void onHoeRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getAction() == RIGHT_CLICK_AIR || event.getAction() == RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getType().toString().endsWith("_HOE")
                    && canShoot(player))  {
                shootProjectile(player, 1.5, Snowball.class);
                setCooldown(player);
            }
        }
    }
}
