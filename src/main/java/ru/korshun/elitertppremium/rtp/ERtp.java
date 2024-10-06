package ru.korshun.elitertppremium.rtp;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.korshun.elitertppremium.api.rtp.Rtp;
import ru.korshun.elitertppremium.api.rtp.RtpCallback;
import ru.korshun.elitertppremium.api.rtp.RtpType;
import ru.korshun.elitertppremium.api.user.SenderType;
import ru.korshun.elitertppremium.utils.ChatUtil;
import ru.korshun.elitertppremium.utils.RtpUtils;

public class ERtp implements Rtp {
    private CommandSender sender;
    private RtpCallback rtpCallback;
    private RtpType rtpType;
    private SenderType senderType;
    private Location location;

    public ERtp(CommandSender sender, RtpCallback rtpCallback, RtpType rtpType, SenderType senderType, Location location) {
        this.sender = sender;
        this.rtpCallback = rtpCallback;
        this.rtpType = rtpType;
        this.senderType = senderType;
        this.location = location;
    }

    @Override
    public CommandSender getSender() {
        return sender;
    }

    @Override
    public RtpCallback getCallback() {
        return rtpCallback;
    }

    @Override
    public RtpType getType() {
        return rtpType;
    }

    @Override
    public SenderType getSenderType() {
        return senderType;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void tp() {
        switch (rtpType) {
            case DEFAULT:
                if(!sender.hasPermission("elitertp.channel.default")) {
                    ChatUtil.messageTranslated(sender, "dont_have_permission");
                    return;
                }
                Player player = (Player) sender;
                if(RtpUtils.isWorldBlocked(player.getWorld())) {
                    ChatUtil.messageTranslated(player, "world-is-blocked");
                    return;
                }
                if(RtpUtils.isBlockBlocked(RtpUtils.getHighestBlockMaterial(this.location))) {
                    this.location = RtpUtils.generateDefaultLocation(player.getWorld());
                    player.teleport(this.location);
                }
        }
    }
}
