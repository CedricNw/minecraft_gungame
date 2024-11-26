package com.minecraft.minecraft_plugin;

import com.minecraft.minecraft_plugin.commands.Kit;
import com.minecraft.minecraft_plugin.guns.*;
import com.minecraft.minecraft_plugin.other.Granade;
import org.bukkit.plugin.java.JavaPlugin;

public class Minecraft_plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Minecraft_plugin aktiviert!");

        // WEAPONS
        getServer().getPluginManager().registerEvents(new Shovel_shoot_rocket(), this);
        getServer().getPluginManager().registerEvents(new Hoe_shoot_snowball(), this);
        getServer().getPluginManager().registerEvents(new Pickaxe_shoot_sniper(), this);
        getServer().getPluginManager().registerEvents(new Axe_shoot_handgun(), this);
        getServer().getPluginManager().registerEvents(new Maze_granade_thrower(), this);

        // COMMANDS
        this.getCommand("kit").setExecutor(new Kit());

        // OTHER
        getServer().getPluginManager().registerEvents(new Granade(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Minecraft_plugin deaktiviert!");
    }
}
