package ru.korshun.elitertppremium.utils;

import ru.korshun.elitertppremium.EliteRtp;

import java.util.List;

public class ConfigUtils {
    public static String getString(String path) {
        return EliteRtp.getInstance().getConfig().getString(path);
    }

    public static int getInt(String path) {
        return EliteRtp.getInstance().getConfig().getInt(path);
    }

    public static List<String> getStringList(String path) {
        return EliteRtp.getInstance().getConfig().getStringList(path);
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
