package ru.korshun.elitertppremium;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.korshun.elitertppremium.api.rtp.Rtp;
import ru.korshun.elitertppremium.api.rtp.RtpCallback;
import ru.korshun.elitertppremium.api.rtp.RtpType;
import ru.korshun.elitertppremium.api.user.SenderType;
import ru.korshun.elitertppremium.api.user.User;
import ru.korshun.elitertppremium.rtp.ERtp;
import ru.korshun.elitertppremium.user.EUser;
import ru.korshun.elitertppremium.utils.ConfigUtils;

import java.io.File;
import java.util.UUID;

public final class EliteRtp extends JavaPlugin {
    private static EliteRtp instance;
    private static YamlConfiguration messages;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadMessages();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static EliteRtp getInstance() {
        return instance;
    }

    private void loadMessages() {
        saveResource("messages/messages_" + ConfigUtils.getDefaultLanguage() + ".yml", false);
        File file = new File(getDataFolder().getAbsolutePath() + "/messages/messages_" + ConfigUtils.getDefaultLanguage() + ".yml");
        messages = YamlConfiguration.loadConfiguration(file);
    }

    public static Rtp createRtpObject(CommandSender sender, RtpCallback rtpCallback, RtpType rtpType, SenderType senderType, Location location) {
        return new ERtp(sender, rtpCallback, rtpType, senderType, location);
    }

    public static User getUser(UUID uuid) {
        return new EUser(uuid);
    }

    public static User getUser(Player player) {
        return new EUser(player.getUniqueId());
    }

    public static YamlConfiguration getMessages() {
        return messages;
    }

    public static User getUser(CommandSender sender) {
        if(sender.getName().equalsIgnoreCase("CONSOLE")) {
            return null;
        }
        return new EUser(((Player) sender).getUniqueId());
    }
}
