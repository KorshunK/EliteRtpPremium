package ru.korshun.elitertppremium.listeners;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.tags.ItemTagType;
import ru.korshun.elitertppremium.EliteRtpPremium;
import ru.korshun.elitertppremium.api.rtp.RtpCallback;
import ru.korshun.elitertppremium.api.rtp.RtpType;
import ru.korshun.elitertppremium.api.user.SenderType;
import ru.korshun.elitertppremium.api.user.User;
import ru.korshun.elitertppremium.menu.MenuManager;
import ru.korshun.elitertppremium.utils.ChatUtil;
import ru.korshun.elitertppremium.utils.ConfigUtils;
import ru.korshun.elitertppremium.utils.RtpUtils;

public class MenuEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent e) {
        User user = EliteRtpPremium.getUser(e.getWhoClicked().getUniqueId());
        if (e.getView().getTitle().equals(ChatUtil.translate(ConfigUtils.getString("menu.title")))) {
            e.setCancelled(true);
            String action = ConfigUtils.getSectionByDisplayNameInMenu(e.getCurrentItem().getItemMeta().getDisplayName()).getString("action");
            if (action != null) {
                if (action.equalsIgnoreCase("rtpDefault")) {
                    user.rtp(EliteRtpPremium.createRtpObject(e.getWhoClicked(), user, RtpType.DEFAULT, SenderType.PLAYER, RtpUtils.generateDefaultLocation(user.getPlayer().getWorld()))).tp();
                }
                else if(action.equalsIgnoreCase("rtpNear")) {
                    user.rtp(EliteRtpPremium.createRtpObject(e.getWhoClicked(), user, RtpType.NEAR, SenderType.PLAYER, RtpUtils.generateNearLocation(user.getPlayer().getWorld(), user))).tp();
                }
                else if(action.equalsIgnoreCase("rtpFar")) {
                    user.rtp(EliteRtpPremium.createRtpObject(e.getWhoClicked(), user, RtpType.FAR, SenderType.PLAYER, RtpUtils.generateFarLocation(user.getPlayer().getWorld()))).tp();
                }
                else if(action.equalsIgnoreCase("rtpWorld")) {
                    if (!user.getPlayer().hasPermission("elitertp.menu")) {
                        ChatUtil.messageTranslated(user.getPlayer(), "dont_have_permission");
                    }
                    user.getPlayer().openInventory(MenuManager.getRtpWorldMenu());
                }
            }
        }
        else if(e.getView().getTitle().equals(ChatUtil.translate(ConfigUtils.getString("world-menu.title")))) {
            e.setCancelled(true);
            World world = Bukkit.getWorld(ChatUtil.translate(e.getCurrentItem().getItemMeta().getDisplayName()));
            user.rtp(EliteRtpPremium.createRtpObject(e.getWhoClicked(), user, RtpType.WORLD, SenderType.PLAYER, RtpUtils.generateWorldLocation(world), world)).tp();
        }
    }
}