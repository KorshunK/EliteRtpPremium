package ru.korshun.elitertppremium.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.korshun.elitertppremium.EliteRtpPremium;
import ru.korshun.elitertppremium.api.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RtpUtils {

    public static Location generateDefaultLocation(World world) {
        Random random = new Random();
        Random random1 = new Random();
        int radius = ConfigUtils.getDefaultChannelRadius();
        int min = Integer.parseInt("-" + radius);
        int radiusX = random.nextInt(radius - min + 1) + min;
        int radiusZ = random1.nextInt(radius - min + 1) + min;
        return new Location(world, radiusX, world.getHighestBlockYAt(radiusX, radiusZ), radiusZ);
    }
    public static Location generateNearLocation(World world, User user) {
        Random xR = new Random();
        Random zR = new Random();
        int x = xR.nextInt(100);
        int z = zR.nextInt(100);
        List<Player> players = new ArrayList<>(world.getPlayers());
        players.remove(user.getPlayer());
        Random random = new Random();
        if(players.size() < 1) {
            return null;
        }
        Player randomPlayer = players.get(random.nextInt(players.size()));
        return new Location(world, randomPlayer.getLocation().getX() - x, world.getHighestBlockYAt(x, z), randomPlayer.getLocation().getZ() - z);
    }
    public static Location generateFarLocation(World world) {
        Random random = new Random();
        Random random1 = new Random();
        int radius = ConfigUtils.getFarChannelRadius();
        int min = Integer.parseInt("-" + radius);
        int radiusX = random.nextInt(radius - min + 1) + min;
        int radiusZ = random1.nextInt(radius - min + 1) + min;
        return new Location(world, radiusX, world.getHighestBlockYAt(radiusX, radiusZ), radiusZ);
    }
    public static Location generateWorldLocation(World world) {
        Random random = new Random();
        Random random1 = new Random();
        int radius = ConfigUtils.getWorldChannelRadius();
        int min = Integer.parseInt("-" + radius);
        int radiusX = random.nextInt(radius - min + 1) + min;
        int radiusZ = random1.nextInt(radius - min + 1) + min;
        return new Location(world, radiusX, world.getHighestBlockYAt(radiusX, radiusZ), radiusZ);
    }
    public static Material getHighestBlockMaterial(Location location) {
        return location.getBlock().getType();
    }
    public static List<Material> getBlockedBlocks() {
        List<Material> list = new ArrayList<>();
        List<String> sList = (List<String>) ConfigUtils.getStringList("blocked-blocks");
        for(String s : sList) {
            list.add(Material.valueOf(s.toUpperCase()));
        }
        return list;
    }
    public static boolean isBlockBlocked(Material material) {
        return getBlockedBlocks().contains(material);
    }
    public static List<World> getBlockedWorlds() {
        List<String> wList = (List<String>) ConfigUtils.getStringList("disabled-worlds");
        List<World> list = new ArrayList<>();
        for(String s : wList) {
            list.add(Bukkit.getWorld(s));
        }
        return list;
    }
    public static boolean isInWorldBlocked(World world) {
        return ConfigUtils.getStringList("disabled-in-worlds").contains(world.getName().toLowerCase());
    }
    public static boolean isWorldBlocked(World world) {
        return getBlockedWorlds().contains(world);
    }
}
