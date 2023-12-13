package com.li2424.glowingitems.light;

import com.li2424.glowingitems.GlowingItems;
import com.li2424.glowingitems.config.Config;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.PlayerInventory;

import java.util.Iterator;

public class Light {

    public static void updateForPlayer(GlowingItems plugin, Player player) {
        PlayerInventory inv = player.getInventory();

        Light.clear(plugin, player);
        for (int i = 0; i < 1; i++) {
            if (checkForAir(player, i)) {
                if (Config.getGlowMaterials(plugin).contains(inv.getItemInMainHand().getType())
                        || Config.getGlowMaterials(plugin).contains(inv.getItemInOffHand().getType())
                || (inv.getItem(EquipmentSlot.HEAD) != null && Config.getGlowMaterials(plugin).contains(inv.getItem(EquipmentSlot.HEAD).getType()))) {
                    Light.addSourceAt(plugin, player, i, 15);
                }
            }
        }
    }

    public static void clear(GlowingItems plugin, Player player) {
        for (Iterator<PlacedLight> it = plugin.savedBlockStates.iterator(); it.hasNext(); ) {
            PlacedLight light = it.next();
            World world = light.getLocation().getWorld();

            if (world != null) {
                if (light.getSource() == player) {
                    world.getBlockAt(light.getLocation())
                            .setType(light.getOriginalMaterial());
                    it.remove();
                }
            }
        }
    }

    public static boolean checkForAir(Player p, int yOffset) {
        World world = p.getWorld();
        Block b = world.getBlockAt(p.getLocation().add(0, yOffset, 0));

        return (b.getType() == Material.AIR);
    }

    public static void addSourceAt(GlowingItems plugin, Player player, int yOffset, int level) {
        World world = player.getWorld();
        Location location = player.getLocation().add(0, yOffset, 0);
        Block block = world.getBlockAt(location);

        plugin.savedBlockStates.add(new PlacedLight(location, player, block.getType()));
        block.setType(Material.LIGHT);
        changeLightLevel(block, level);
    }

    public static void changeLightLevel(Block lightBlock, int level) {
        if (lightBlock.getType() != Material.LIGHT) {
            throw new IllegalArgumentException("Block is not a light block");
        }
        Levelled levelled = (Levelled) lightBlock.getBlockData();
        levelled.setLevel(level);

        lightBlock.setBlockData(levelled);
    }
}
