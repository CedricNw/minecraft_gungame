package com.minecraft.minecraft_plugin.guns;

import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class Maze_granade_thrower  extends Weapon implements Listener {

    public Maze_granade_thrower() {
        super(1000, 12, 10000, 0.5, Material.EGG);
    }

    @EventHandler
    public void onMazeRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == RIGHT_CLICK_AIR || event.getAction() == RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getType()== Material.MACE
                    && canShoot(player)) {
                shootProjectile(player, 2.0, Egg.class);
                setCooldown(player);
            }
        }
    }
}
