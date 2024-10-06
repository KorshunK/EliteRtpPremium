package ru.korshun.elitertppremium.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class RtpUtils {

    public static Location generateDefaultLocation(World world) {
        Random random = new Random();
        int radius = ConfigUtils.getDefaultChannelRadius();
        int min = Integer.parseInt("-" + radius);
        int radiusX = random.nextInt(radius - min + 1) + min;
        int radiusZ = random.nextInt(radius - min + 1) + min;
        return new Location(world, radiusX, world.getHighestBlockYAt(radiusX, radiusZ), radiusZ);
    }
    public static Location generateFarLocation(World world) {
        Random random = new Random();
        int radius = ConfigUtils.getFarChannelRadius();
        int min = Integer.parseInt("-" + radius);
        int radiusX = random.nextInt(radius - min + 1) + min;
        int radiusZ = random.nextInt(radius - min + 1) + min;
        return new Location(world, radiusX, world.getHighestBlockYAt(radiusX, radiusZ), radiusZ);
    }
    public static Location generateWorldLocation(World world) {
        Random random = new Random();
        int radius = ConfigUtils.getWorldChannelRadius();
        int min = Integer.parseInt("-" + radius);
        int radiusX = random.nextInt(radius - min + 1) + min;
        int radiusZ = random.nextInt(radius - min + 1) + min;
        return new Location(world, radiusX, world.getHighestBlockYAt(radiusX, radiusZ), radiusZ);
    }
    public static Material getHighestBlockMaterial(Location location) {
        World world = location.getWorld();
        Block block = world.getHighestBlockAt(location);
        return block.getType();
    }
    public static List<Material> getBlockedBlocks() {
        List<Material> list = new ArrayList<>();
        List<String> sList = ConfigUtils.getStringList("blocked-blocks");
        for(String s : sList) {
            list.add(Material.valueOf(s.toUpperCase()));
        }
        return list;
    }
    public static boolean isBlockBlocked(Material material) {
        return getBlockedBlocks().contains(material);
    }
    public static List<World> getBlockedWorlds() {
        List<String> wList = ConfigUtils.getStringList("disabled-worlds");
        List<World> list = new ArrayList<>();
        for(String s : wList) {
            list.add(Bukkit.getWorld(s));
        }
        return list;
    }
    public static boolean isWorldBlocked(World world) {
        return getBlockedWorlds().contains(world);
    }
}
