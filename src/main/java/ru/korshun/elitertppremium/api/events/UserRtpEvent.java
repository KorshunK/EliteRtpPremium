package ru.korshun.elitertppremium.api.events;

import com.sun.istack.internal.Nullable;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import ru.korshun.elitertppremium.api.rtp.RtpType;
import ru.korshun.elitertppremium.api.user.SenderType;
import ru.korshun.elitertppremium.api.user.User;

public class UserRtpEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private CommandSender sender;
    private User user;
    private RtpType rtpType;
    private SenderType senderType;
    private Location location;
    @Nullable private World world;

    public UserRtpEvent(CommandSender sender, User user, RtpType rtpType, SenderType senderType, Location location) {
        this.sender = sender;
        this.user = user;
        this.rtpType = rtpType;
        this.senderType = senderType;
        this.location = location;
    }

    public UserRtpEvent(CommandSender sender, User user, RtpType rtpType, SenderType senderType, Location location, World world) {
        this.sender = sender;
        this.user = user;
        this.rtpType = rtpType;
        this.senderType = senderType;
        this.location = location;
        this.world = world;
    }

    public CommandSender getSender() {
        return sender;
    }

    public User getUser() {
        return user;
    }

    public RtpType getRtpType() {
        return rtpType;
    }

    public SenderType getSenderType() {
        return senderType;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
