package ru.korshun.elitertppremium.utils;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.korshun.elitertppremium.EliteRtpPremium;
import ru.korshun.elitertppremium.api.rtp.MessageType;
import ru.korshun.elitertppremium.api.rtp.RtpType;
import ru.korshun.elitertppremium.api.user.SenderType;
import ru.korshun.elitertppremium.api.user.User;

public class ChatUtil {
    public static void messageTranslated(CommandSender sender, String path) {
        User user = EliteRtpPremium.getUser(sender);
        user.rtp(EliteRtpPremium.createRtpObject(sender, user, RtpType.DEFAULT, SenderType.PLAYER, RtpUtils.generateDefaultLocation(user.getPlayer().getWorld()))).tp();
        sender.sendMessage(translate(getMessage(path)));
    }

    public static void title(User user, String title, String subtitle, int i, int i1, int i2) {
        user.getPlayer().sendTitle(title, subtitle, i, i1, i2);
    }

    public static MessageType getMessageType() {
        return MessageType.valueOf(getMessage("rtp-success.type"));
    }

    public static String translate(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void message(CommandSender sender, String msg) {
        sender.sendMessage(msg);
    }

    public static String getMessage(String path) {
        return EliteRtpPremium.getMessages().getString(path);
    }
}
