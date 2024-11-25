package com.minecraft.minecraft_plugin;

import com.minecraft.minecraft_plugin.guns.Hoe_shoot_snowball;
import com.minecraft.minecraft_plugin.guns.Pickaxe_shoot_sniper;
import com.minecraft.minecraft_plugin.guns.Shovel_shoot_rocket;
import org.bukkit.plugin.java.JavaPlugin;

public class Minecraft_plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Minecraft_plugin aktiviert!");

        getServer().getPluginManager().registerEvents(new Shovel_shoot_rocket(), this);
        getServer().getPluginManager().registerEvents(new Hoe_shoot_snowball(), this);
        getServer().getPluginManager().registerEvents(new Pickaxe_shoot_sniper(), this);

    }

    @Override
    public void onDisable() {
        getLogger().info("Minecraft_plugin deaktiviert!");
    }
}
