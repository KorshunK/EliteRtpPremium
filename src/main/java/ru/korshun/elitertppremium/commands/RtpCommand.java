package ru.korshun.elitertppremium.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.korshun.elitertppremium.menu.MenuManager;

public class RtpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Inventory menu = MenuManager.getRtpMenu();
            Player player = (Player) sender;
            player.openInventory(menu);
        }
        return false;
    }
}
