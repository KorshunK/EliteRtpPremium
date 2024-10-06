package ru.korshun.elitertppremium.api.user;

import org.bukkit.entity.Player;
import ru.korshun.elitertppremium.api.rtp.Rtp;

public interface User {
    Rtp rtp(Rtp rtp);
    Player getPlayer();
}
