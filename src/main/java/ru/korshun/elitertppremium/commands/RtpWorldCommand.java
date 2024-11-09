package ru.korshun.elitertppremium.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.korshun.elitertppremium.EliteRtpPremium;
import ru.korshun.elitertppremium.api.rtp.RtpType;
import ru.korshun.elitertppremium.api.user.SenderType;
import ru.korshun.elitertppremium.api.user.User;
import ru.korshun.elitertppremium.menu.MenuManager;
import ru.korshun.elitertppremium.utils.ConfigUtils;
import ru.korshun.elitertppremium.utils.RtpUtils;

public class RtpWorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length == 0) {
                User user = EliteRtpPremium.getUser(((Player) sender).getUniqueId());
                user.getPlayer().openInventory(MenuManager.getRtpWorldMenu());
                return true;
            }
            if(args.length == 1) {
                User user = EliteRtpPremium.getUser(((Player) sender).getUniqueId());
                user.rtp(EliteRtpPremium.createRtpObject(sender, user, RtpType.WORLD, SenderType.PLAYER, RtpUtils.generateFarLocation(Bukkit.getWorld(args[0])), Bukkit.getWorld(args[0]))).tp();
                return true;
            }
            else if(args.length == 2) {
                User user = EliteRtpPremium.getUser(Bukkit.getPlayer(args[0]));
                user.rtp(EliteRtpPremium.createRtpObject(sender, user, RtpType.WORLD, SenderType.ADMIN, RtpUtils.generateWorldLocation(Bukkit.getWorld(args[1])), Bukkit.getWorld(args[1]))).tp();
                return true;
            }
        }
        return false;
    }
}
