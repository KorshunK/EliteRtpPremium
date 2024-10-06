package ru.korshun.elitertppremium.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import ru.korshun.elitertppremium.EliteRtp;

public class ChatUtil {
    public static void messageTranslated(CommandSender sender, String path) {
        sender.sendMessage(translate(getMessage(path)));
    }

    public static String translate(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void message(CommandSender sender, String msg) {
        sender.sendMessage(msg);
    }

    public static String getMessage(String path) {
        return EliteRtp.getMessages().getString(path);
    }
}
