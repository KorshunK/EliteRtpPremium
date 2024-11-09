package ru.korshun.elitertppremium.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import ru.korshun.elitertppremium.api.events.UserRtpEvent;
import ru.korshun.elitertppremium.api.user.SenderType;
import ru.korshun.elitertppremium.utils.ChatUtil;
import ru.korshun.elitertppremium.utils.ConfigUtils;

public class Events implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRtp(UserRtpEvent e) {
        if(ConfigUtils.getStringList("particles.effect") != null) {
            e.getUser().getPlayer().getWorld().spawnParticle(ConfigUtils.getRtpParticle(), e.getUser().getPlayer().getLocation(), ConfigUtils.getRtpParticlesCount());
        }
    }
}
