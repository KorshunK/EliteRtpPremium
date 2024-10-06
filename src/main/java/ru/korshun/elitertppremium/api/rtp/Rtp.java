package ru.korshun.elitertppremium.api.rtp;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import ru.korshun.elitertppremium.api.user.SenderType;
import ru.korshun.elitertppremium.api.user.User;

public interface Rtp {
    CommandSender getSender();
    RtpCallback getCallback();
    RtpType getType();
    SenderType getSenderType();
    Location getLocation();
    void tp();
}
