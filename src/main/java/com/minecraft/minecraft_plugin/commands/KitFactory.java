package com.minecraft.minecraft_plugin.commands;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitFactory {

    public static void giveKit(Player player, String name, List<ItemStack> items) {
        player.getInventory().clear();

        for (ItemStack item : items) {
            player.getInventory().addItem(item);
        }

        player.sendMessage("Du hast Kit " + name + " erhalten!");
    }
}
