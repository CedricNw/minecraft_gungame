package com.minecraft.minecraft_plugin.guns;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Pickaxe_shoot_sniper extends Weapon implements Listener {

    boolean zoomed = false;

    public Pickaxe_shoot_sniper() {
        super(2500, 8, 6000, 16, Material.EGG);
    }

    @EventHandler
    public void onPickaxeInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().toString().endsWith("_PICKAXE")) {
            switch (event.getAction()) {
                case LEFT_CLICK_AIR:
                case LEFT_CLICK_BLOCK:
                    if(zoomed) {
                        deactivateZoom(player);
                    } else {
                        activateZoom(player);
                    }
                    break;

                case RIGHT_CLICK_AIR:
                case RIGHT_CLICK_BLOCK:
                    if (canShoot(player)) {
                        shootProjectile(player, 10, Snowball.class);
                        setCooldown(player);
                    }
                    break;

                default:
                    break;
            }
        }
    }

    @EventHandler
    public void onSlotChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
        if (newItem == null || !newItem.getType().toString().endsWith("_PICKAXE")) {
            deactivateZoom(player);
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

    private void activateZoom(Player player) {
        zoomed = true;
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 5, true, false));
    }

    private void deactivateZoom(Player player) {
        zoomed = false;
        player.removePotionEffect(PotionEffectType.SLOWNESS);
    }
}




