package ru.korshun.elitertppremium.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ru.korshun.elitertppremium.EliteRtpPremium;
import ru.korshun.elitertppremium.api.rtp.Rtp;
import ru.korshun.elitertppremium.api.rtp.RtpCallback;
import ru.korshun.elitertppremium.api.rtp.RtpType;
import ru.korshun.elitertppremium.api.user.SenderType;
import ru.korshun.elitertppremium.api.user.User;
import ru.korshun.elitertppremium.utils.ChatUtil;
import ru.korshun.elitertppremium.utils.ConfigUtils;
import ru.korshun.elitertppremium.utils.RtpUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EliteRtpCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("reload")) {
                    if(!sender.hasPermission("elitertp.reload")) {
                        ChatUtil.messageTranslated(sender, "dont_have_permission");
                        return false;
                    }
                    EliteRtpPremium.getInstance().reloadConfig();
                    try {
                        EliteRtpPremium.getMessages().load(new File(EliteRtpPremium.getInstance().getDataFolder().getAbsolutePath() + "/messages/messages_" + ConfigUtils.getDefaultLanguage() + ".yml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }
                    ChatUtil.messageTranslated(sender, "plugin-reloaded");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if(args.length == 1) {
            list.add("reload");
            return list;
        }
        return new ArrayList<>();
    }
}
