package ru.korshun.elitertppremium.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.korshun.elitertppremium.EliteRtpPremium;
import ru.korshun.elitertppremium.api.rtp.RtpType;
import ru.korshun.elitertppremium.api.user.SenderType;
import ru.korshun.elitertppremium.api.user.User;
import ru.korshun.elitertppremium.utils.RtpUtils;

public class RtpFarCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length == 0) {
                User user = EliteRtpPremium.getUser(((Player) sender).getUniqueId());
                user.rtp(EliteRtpPremium.createRtpObject(sender, user, RtpType.FAR, SenderType.PLAYER, RtpUtils.generateFarLocation(user.getPlayer().getWorld()))).tp();
                return true;
            }
            else if(args.length == 1) {
                User user = EliteRtpPremium.getUser(Bukkit.getPlayer(args[0]));
                user.rtp(EliteRtpPremium.createRtpObject(sender, user, RtpType.FAR, SenderType.ADMIN, RtpUtils.generateFarLocation(user.getPlayer().getWorld()))).tp();
                return true;
            }
        } else {
            if(args.length == 1) {
                User user = EliteRtpPremium.getUser(Bukkit.getPlayer(args[0]));
                user.rtp(EliteRtpPremium.createRtpObject(sender, user, RtpType.FAR, SenderType.CONSOLE, RtpUtils.generateFarLocation(user.getPlayer().getWorld()))).tp();
                return true;
            }
        }
        return false;
    }
}
