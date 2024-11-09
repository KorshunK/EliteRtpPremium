package ru.korshun.elitertppremium;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.korshun.elitertppremium.api.rtp.Rtp;
import ru.korshun.elitertppremium.api.rtp.RtpType;
import ru.korshun.elitertppremium.api.user.SenderType;
import ru.korshun.elitertppremium.api.user.User;
import ru.korshun.elitertppremium.commands.*;
import ru.korshun.elitertppremium.hook.ServerHook;
import ru.korshun.elitertppremium.listeners.Events;
import ru.korshun.elitertppremium.listeners.MenuEvent;
import ru.korshun.elitertppremium.menu.MenuManager;
import ru.korshun.elitertppremium.rtp.ERtp;
import ru.korshun.elitertppremium.user.EUser;
import ru.korshun.elitertppremium.utils.ConfigUtils;

import java.io.File;
import java.util.UUID;

public final class EliteRtpPremium extends JavaPlugin {
    private static EliteRtpPremium instance;
    private static YamlConfiguration messages;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadMessages();
        registerCommands();
        registerEvents();
        setup();
    }

    @Override
    public void onDisable() {

    }

    public static void disablePlugin() {
        EliteRtpPremium.getInstance().getServer().getPluginManager().disablePlugin(EliteRtpPremium.getInstance());
    }

    private void setup() {
        MenuManager.setup();
        ServerHook.hook();
    }

    public void callEvent(Event event) {
        this.getServer().getPluginManager().callEvent(event);
    }

    private void registerEvents() {
        registerEvent(new Events());
        registerEvent(new MenuEvent());
    }

    public void registerEvent(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    private void registerCommands() {
        registerCommand("elitertp", new EliteRtpCommand());
        registerCommand("rtpdefault", new RtpDefaultCommand());
        registerCommand("rtpnear", new RtpNearCommand());
        registerCommand("rtpfar", new RtpFarCommand());
        registerCommand("rtpworld", new RtpWorldCommand());
        registerCommand("rtp", new RtpCommand());
    }

    public void registerCommand(String name, CommandExecutor commandExecutor) {
        this.getCommand(name).setExecutor(commandExecutor);
    }

    public static EliteRtpPremium getInstance() {
        return instance;
    }

    private void loadMessages() {
        File file = new File(getDataFolder().getAbsolutePath() + "/messages/messages_" + ConfigUtils.getDefaultLanguage() + ".yml");
        if(!file.exists()) {
            saveResource("messages/messages_" + ConfigUtils.getDefaultLanguage() + ".yml", false);
        }
        messages = YamlConfiguration.loadConfiguration(file);
    }

    public static Rtp createRtpObject(CommandSender sender, User user, RtpType rtpType, SenderType senderType, Location location) {
        return new ERtp(sender, user, rtpType, senderType, location);
    }
    public static Rtp createRtpObject(CommandSender sender, User user, RtpType rtpType, SenderType senderType, Location location, World world) {
        return new ERtp(sender, user, rtpType, senderType, location, world);
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
