package com.minecraft.minecraft_plugin.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Kit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von einem Spieler verwendet werden.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage("Bitte gib ein Kit an. Beispiel: /kit sniper");
            return true;
        }

        String kitName = args[0].toLowerCase();
        List<ItemStack> kitItems = getKitByName(kitName);

        if (kitItems == null) {
            player.sendMessage("Das Kit \"" + kitName + "\" existiert nicht.");
            return true;
        }

        KitFactory.giveKit(player, kitName, kitItems);
        return true;
    }

    private List<ItemStack> getKitByName(String kitName) {
        switch (kitName) {
            case "sniper":
                return createSniperKit();
            case "ak":
                return createMaschineGunKit();
            case "rocket":
                return createRocketLauncherKit();
            case "granade":
                return createGranadeLauncherKit();
            default:
                return null;
        }
    }

    private List<ItemStack> createSniperKit() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.DIAMOND_PICKAXE));
        items.add(new ItemStack(Material.DIAMOND_SWORD));
        items.add(new ItemStack(Material.NETHERITE_AXE));

        items.add(new ItemStack(Material.NETHERITE_BOOTS));
        items.add(new ItemStack(Material.NETHERITE_LEGGINGS));
        items.add(new ItemStack(Material.NETHERITE_CHESTPLATE));
        items.add(new ItemStack(Material.NETHERITE_HELMET));

        items.add(new ItemStack(Material.SPECTRAL_ARROW, 128));
        items.add(new ItemStack(Material.FLINT, 128));

        return items;
    }
    private List<ItemStack> createMaschineGunKit() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.NETHERITE_HOE));
        items.add(new ItemStack(Material.EGG, 3));

        items.add(new ItemStack(Material.NETHERITE_BOOTS));
        items.add(new ItemStack(Material.NETHERITE_LEGGINGS));
        items.add(new ItemStack(Material.NETHERITE_CHESTPLATE));
        items.add(new ItemStack(Material.NETHERITE_HELMET));

        items.add(new ItemStack(Material.ARROW, 660));

        return items;
    }
    private List<ItemStack> createRocketLauncherKit() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.NETHERITE_SHOVEL));
        items.add(new ItemStack(Material.NETHERITE_AXE));
        items.add(new ItemStack(Material.EGG, 8));

        items.add(new ItemStack(Material.NETHERITE_BOOTS));
        items.add(new ItemStack(Material.NETHERITE_LEGGINGS));
        items.add(new ItemStack(Material.NETHERITE_CHESTPLATE));
        items.add(new ItemStack(Material.NETHERITE_HELMET));

        items.add(new ItemStack(Material.FIRE_CHARGE, 80));
        items.add(new ItemStack(Material.SPECTRAL_ARROW, 128));

        return items;
    }
    private List<ItemStack> createGranadeLauncherKit() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.MACE));
        items.add(new ItemStack(Material.NETHERITE_AXE));
        items.add(new ItemStack(Material.EGG, 62));

        items.add(new ItemStack(Material.NETHERITE_BOOTS));
        items.add(new ItemStack(Material.NETHERITE_LEGGINGS));
        items.add(new ItemStack(Material.NETHERITE_CHESTPLATE));
        items.add(new ItemStack(Material.NETHERITE_HELMET));

        items.add(new ItemStack(Material.SPECTRAL_ARROW, 128));

        return items;
    }
}
