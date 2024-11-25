package com.minecraft.minecraft_plugin.guns;

import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class Pickaxe_shoot_sniper implements Listener {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private static final long SHOOT_COOLDOWN = 2500;
    boolean zoomed = false;

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
                        shootSniperSnowball(player);
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

    @EventHandler
    public void onSnowballHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getDamager();

            if ("SniperSnowball".equals(snowball.getCustomName()) && snowball.getShooter() instanceof Player) {
                event.setDamage(16.0);
            }
        }
    }

    private void shootSniperSnowball(Player player) {
        Snowball snowball = player.launchProjectile(Snowball.class);

        Vector direction = player.getLocation().getDirection().multiply(10);
        snowball.setVelocity(direction);
        snowball.setGravity(false);
        snowball.setShooter(player);
        snowball.setCustomName("SniperSnowball");
    }

    private void activateZoom(Player player) {
        zoomed = true;
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 5, true, false));
    }

    private void deactivateZoom(Player player) {
        zoomed = false;
        player.removePotionEffect(PotionEffectType.SLOWNESS);
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




