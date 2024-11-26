package com.minecraft.minecraft_plugin.guns;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.UUID;

public class Weapon implements Listener {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final long SHOOT_COOLDOWN;
    private final HashMap<UUID, Long> reloads = new HashMap<>();
    public final int MAGAZINE_SIZE;
    private final long RELOAD_TIME;
    public int shotsFired;
    private final double DAMAGE;
    private final Material MATERIAL;

    public Weapon(int cooldownTime, int magazineSize, long reloadTime, double damage, Material material) {
        SHOOT_COOLDOWN = cooldownTime;
        MAGAZINE_SIZE = magazineSize;
        RELOAD_TIME = reloadTime;
        DAMAGE = damage;
        shotsFired = 1;
        MATERIAL = material;
    }
    public boolean canShoot(Player player) {
        UUID playerId = player.getUniqueId();
        boolean hasMonition = false;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == MATERIAL) {
                hasMonition = true;
            }
        }

        if(!reloads.containsKey(playerId) && hasMonition) {
            if (!cooldowns.containsKey(playerId)) {
                return true;
            } else {
                long lastShotTime = cooldowns.get(playerId);
                return (System.currentTimeMillis() - lastShotTime) >= SHOOT_COOLDOWN;
            }
        } else {
            long lastShotTime = reloads.get(playerId);
            shotsFired = 0;
            return (System.currentTimeMillis() - lastShotTime) >= RELOAD_TIME;
        }
    }

    public void shootProjectile(Player player, double travelspeed, Class projectileClass) {
        Projectile snowball = player.launchProjectile(projectileClass);
        snowball.setVelocity(player.getLocation().getDirection().multiply(travelspeed));
        snowball.setShooter(player);
        snowball.setCustomName("CustomBullet");
        shotsFired += 1;
        removeBullet(player);
        magazineNotEmpty(player);
    }

    @EventHandler
    public void onSnowballHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getDamager();
            if ("CustomBullet".equals(snowball.getCustomName()) && snowball.getShooter() instanceof Player) {
                    event.setDamage(DAMAGE);
            }
        }
    }

    public void removeBullet(Player player) {
        boolean setedMinus = true;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == MATERIAL && setedMinus) {
                setedMinus = false;
                item.setAmount(item.getAmount() - 1);
            }
        }
        player.sendActionBar((MAGAZINE_SIZE-shotsFired)+"/"+MAGAZINE_SIZE);
    }

    public void magazineNotEmpty(Player player) {
        UUID playerId = player.getUniqueId();
        if(MAGAZINE_SIZE <= shotsFired) {
            setReload(player);
        } else {
            reloads.remove(playerId);
        }
    }

    public void setReload(Player player) {
        UUID playerId = player.getUniqueId();
        player.sendActionBar("Reloading");
        reloads.put(playerId, System.currentTimeMillis());
    }

    public void setCooldown(Player player) {
        UUID playerId = player.getUniqueId();
        cooldowns.put(playerId, System.currentTimeMillis());
    }
}
