package ru.korshun.elitertppremium.utils;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import ru.korshun.elitertppremium.EliteRtpPremium;
import ru.korshun.elitertppremium.api.rtp.MessageType;
import ru.korshun.elitertppremium.api.rtp.RtpType;

import java.util.List;

public class ConfigUtils {
    public static String getString(String path) {
        return EliteRtpPremium.getInstance().getConfig().getString(path);
    }

    public static int getInt(String path) {
        return EliteRtpPremium.getInstance().getConfig().getInt(path);
    }

    public static int getRemoveFood(RtpType rtpType) {
        return getInt("channels." + rtpType.name().toLowerCase() + ".foodRemove");
    }
    public static int getRemoveHeal(RtpType rtpType) {
        return getInt("channels." + rtpType.name().toLowerCase() + ".healRemove");
    }

    public static ConfigurationSection getConfigurationSection(String path) {
        return EliteRtpPremium.getInstance().getConfig().getConfigurationSection(path);
    }

    public static int getStartSlot() {
        return getInt("world-menu.start-slot");
    }

    public static int getEndSlot() {
        return getInt("world-menu.end-slot");
    }

    public static ConfigurationSection getSectionByDisplayNameInMenu(String s) {
        ConfigurationSection section = getConfigurationSection("menu.items");
        for(String s1 : section.getKeys(false)) {
            if(ChatUtil.translate(section.getConfigurationSection(s1).getString("display_name")).equals(s)) {
                return section.getConfigurationSection(s1);
            }
        }
        return null;
    }

    public static List<?> getStringList(String path) {
        return EliteRtpPremium.getInstance().getConfig().getList(path);
    }

    public static World getDefaultWorld() {
        return Bukkit.getWorld(getString("channels.world.default_world"));
    }

    public static Particle getRtpParticle() {
        return Particle.valueOf(getString("particles.effect"));
    }

    public static int getRtpParticlesCount() {
        return getInt("particles.count");
    }

    public static int getDefaultCooldown() {
        return getInt("channels.default.cooldown");
    }
    public static int getNearCooldown() {
        return getInt("channels.near.cooldown");
    }
    public static int getFarCooldown() {
        return getInt("channels.far.cooldown");
    }
    public static int getWorldCooldown() {
        return getInt("channels.world.cooldown");
    }

    public static int getDefaultChannelRadius() {
        return getInt("channels.default.radius");
    }

    public static int getFarChannelRadius() {
        return getInt("channels.far.radius");
    }

    public static int getWorldChannelRadius() {
        return getInt("channels.world.radius");
    }

    public static String getDefaultLanguage() {
        return getString("language");
    }
}
