package ru.korshun.elitertppremium.rtp;

import com.sun.istack.internal.Nullable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.korshun.elitertppremium.EliteRtpPremium;
import ru.korshun.elitertppremium.api.cooldown.Cooldown;
import ru.korshun.elitertppremium.api.events.UserRtpEvent;
import ru.korshun.elitertppremium.api.rtp.MessageType;
import ru.korshun.elitertppremium.api.rtp.Rtp;
import ru.korshun.elitertppremium.api.rtp.RtpCallback;
import ru.korshun.elitertppremium.api.rtp.RtpType;
import ru.korshun.elitertppremium.api.user.SenderType;
import ru.korshun.elitertppremium.api.user.User;
import ru.korshun.elitertppremium.utils.ChatUtil;
import ru.korshun.elitertppremium.utils.ConfigUtils;
import ru.korshun.elitertppremium.utils.RtpUtils;

public class ERtp extends Cooldown implements Rtp {
    private CommandSender sender;
    private User user;
    private RtpCallback rtpCallback;
    private RtpType rtpType;
    private SenderType senderType;
    private Location location;
    @Nullable
    private World world;

    public ERtp(CommandSender sender, User user, RtpType rtpType, SenderType senderType, Location location) {
        this.sender = sender;
        this.user = user;
        this.rtpType = rtpType;
        this.senderType = senderType;
        this.location = location;
    }

    public ERtp(CommandSender sender, User user, RtpType rtpType, SenderType senderType, Location location, @Nullable World world) {
        this.sender = sender;
        this.user = user;
        this.rtpType = rtpType;
        this.senderType = senderType;
        this.location = location;
        this.world = world;
    }

    @Override
    public CommandSender getSender() {
        return sender;
    }

    @Override
    public User getUser() {
        return user;
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
    public RtpCallback tp() {
        switch (rtpType) {
            case DEFAULT:
                switch (senderType) {
                    case PLAYER:
                        if (!sender.hasPermission("elitertp.channel.default")) {
                            ChatUtil.messageTranslated(sender, "dont_have_permission");
                            return RtpCallback.DONT_HAVE_PERMISSION;
                        }
                    case ADMIN:
                        if (!sender.hasPermission("elitertp.channel.default.others")) {
                            ChatUtil.messageTranslated(sender, "dont_have_permission");
                            return RtpCallback.DONT_HAVE_PERMISSION;
                        }
                    case CONSOLE:
                        if (!sender.hasPermission("elitertp.channel.default.others")) {
                            ChatUtil.messageTranslated(sender, "dont_have_permission");
                            return RtpCallback.DONT_HAVE_PERMISSION;
                        }
                }
                if(hasCooldown(user.getPlayer().getUniqueId(), RtpType.DEFAULT, ConfigUtils.getDefaultCooldown())) {
                    if(!user.getPlayer().hasPermission("elitertp.default.cooldown.bypass")) {
                        ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("player-has-cooldown").replace("{cooldown}", String.valueOf(ConfigUtils.getDefaultCooldown())).replace("{cooldown_remained}", String.valueOf(ConfigUtils.getDefaultCooldown() - getCooldown(user.getPlayer().getUniqueId(), RtpType.DEFAULT)))));
                        return RtpCallback.COOLDOWN;
                    }
                }
                Player player = user.getPlayer();
                if(RtpUtils.isWorldBlocked(player.getWorld())) {
                    ChatUtil.messageTranslated(player, "world-is-blocked");
                    return RtpCallback.WORLD_BLOCKED;
                }
                this.location = RtpUtils.generateDefaultLocation(player.getWorld());
                if(RtpUtils.isBlockBlocked(this.location.getBlock().getType())) {
                    tp();
                    return RtpCallback.SUCCESS;
                }
                if(player.getFoodLevel() - ConfigUtils.getRemoveFood(RtpType.DEFAULT) <= 0) {
                    ChatUtil.messageTranslated(sender, "not-enough-food");
                    return RtpCallback.NOT_ENOUGH_FOOD;
                }
                if(player.getHealth() - ConfigUtils.getRemoveHeal(RtpType.DEFAULT) <= 0) {
                    ChatUtil.messageTranslated(sender, "not-enough-heal");
                    return RtpCallback.NOT_ENOUGH_HEAL;
                }
                setCooldown(user.getPlayer().getUniqueId(), RtpType.DEFAULT);
                player.teleport(new Location(this.location.getWorld(), this.location.getX(), this.location.getY() + 1, this.location.getZ()));
                user.getPlayer().getWorld().spawnParticle(ConfigUtils.getRtpParticle(), user.getPlayer().getLocation(), ConfigUtils.getRtpParticlesCount());
                player.setFoodLevel(player.getFoodLevel() - ConfigUtils.getRemoveFood(RtpType.DEFAULT));
                player.setHealth(player.getHealth() - ConfigUtils.getRemoveHeal(RtpType.DEFAULT));
                EliteRtpPremium.getInstance().callEvent(new UserRtpEvent(sender, user, RtpType.DEFAULT, senderType, location));
                if(ChatUtil.getMessageType() == MessageType.MESSAGE) {
                    if(senderType == SenderType.PLAYER) {
                        ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("rtp-success.rtp-success-message").replace("{pos_x}", String.valueOf(this.location.getX())).replace("{pos_y}", String.valueOf(this.location.getY())).replace("{pos_z}", String.valueOf(this.location.getZ()))));
                    }
                    else if(senderType == SenderType.ADMIN || senderType == SenderType.CONSOLE) {
                        ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("rtp-success.rtp-success-message").replace("{player_name}", user.getPlayer().getName()).replace("{pos_x}", String.valueOf(this.location.getX())).replace("{pos_y}", String.valueOf(this.location.getY())).replace("{pos_z}", String.valueOf(this.location.getZ()))));
                    }
                }
                else if(ChatUtil.getMessageType() == MessageType.TITLE) {
                    ChatUtil.title(user, ChatUtil.translate(ChatUtil.getMessage("rtp-success.rtp-success-message")), ChatUtil.translate(ChatUtil.getMessage("rtp-success.subtitle").replace("{pos_x}", String.valueOf(this.location.getX())).replace("{pos_y}", String.valueOf(this.location.getY())).replace("{pos_z}", String.valueOf(this.location.getZ()))), 20, 60, 20);
                }
                return RtpCallback.SUCCESS;
            case NEAR:
                switch (senderType) {
                    case PLAYER:
                        if (!sender.hasPermission("elitertp.channel.near")) {
                            ChatUtil.messageTranslated(sender, "dont_have_permission");
                            return RtpCallback.DONT_HAVE_PERMISSION;
                        }
                    case ADMIN:
                        if (!sender.hasPermission("elitertp.channel.near.others")) {
                            ChatUtil.messageTranslated(sender, "dont_have_permission");
                            return RtpCallback.DONT_HAVE_PERMISSION;
                        }
                    case CONSOLE:
                        if (!sender.hasPermission("elitertp.channel.near.others")) {
                            ChatUtil.messageTranslated(sender, "dont_have_permission");
                            return RtpCallback.DONT_HAVE_PERMISSION;
                        }
                }
                if(hasCooldown(user.getPlayer().getUniqueId(), RtpType.NEAR, ConfigUtils.getNearCooldown())) {
                    if(!user.getPlayer().hasPermission("elitertp.near.cooldown.bypass")) {
                        ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("player-has-cooldown").replace("{cooldown}", String.valueOf(ConfigUtils.getNearCooldown())).replace("{cooldown_remained}", String.valueOf(ConfigUtils.getNearCooldown() - getCooldown(user.getPlayer().getUniqueId(), RtpType.NEAR)))));
                        return RtpCallback.COOLDOWN;
                    }
                }
                Player player1 = user.getPlayer();
                if(player1.getWorld().getPlayers().size() < ConfigUtils.getInt("channels.near.min-players-for-tp")) {
                    ChatUtil.messageTranslated(sender, "rtp-near-players-count-less-min");
                    return RtpCallback.MIN_PLAYERS;
                }
                if(RtpUtils.isWorldBlocked(player1.getWorld())) {
                    ChatUtil.messageTranslated(player1, "world-is-blocked");
                    return RtpCallback.WORLD_BLOCKED;
                }
                this.location = RtpUtils.generateNearLocation(player1.getWorld(), user);
                if(RtpUtils.isBlockBlocked(this.location.getBlock().getType())) {
                    tp();
                    return RtpCallback.SUCCESS;
                }
                if(player1.getFoodLevel() - ConfigUtils.getRemoveFood(RtpType.NEAR) <= 0) {
                    ChatUtil.messageTranslated(sender, "not-enough-food");
                    return RtpCallback.NOT_ENOUGH_FOOD;
                }
                if(player1.getHealth() - ConfigUtils.getRemoveHeal(RtpType.NEAR) <= 0) {
                    ChatUtil.messageTranslated(sender, "not-enough-heal");
                    return RtpCallback.NOT_ENOUGH_HEAL;
                }
                setCooldown(user.getPlayer().getUniqueId(), RtpType.NEAR);
                player1.teleport(new Location(this.location.getWorld(), this.location.getX(), this.location.getY() + 1, this.location.getZ()));
                player1.setFoodLevel(player1.getFoodLevel() - ConfigUtils.getRemoveFood(RtpType.NEAR));
                player1.setHealth(player1.getHealth() - ConfigUtils.getRemoveHeal(RtpType.NEAR));
                EliteRtpPremium.getInstance().callEvent(new UserRtpEvent(sender, user, RtpType.NEAR, senderType, location));
                if(ChatUtil.getMessageType() == MessageType.MESSAGE) {
                    if(senderType == SenderType.PLAYER) {
                        ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("rtp-success.rtp-success-message").replace("{pos_x}", String.valueOf(this.location.getX())).replace("{pos_y}", String.valueOf(this.location.getY())).replace("{pos_z}", String.valueOf(this.location.getZ()))));
                    }
                    else if(senderType == SenderType.ADMIN || senderType == SenderType.CONSOLE) {
                        ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("rtp-success.rtp-success-message").replace("{player_name}", user.getPlayer().getName()).replace("{pos_x}", String.valueOf(this.location.getX())).replace("{pos_y}", String.valueOf(this.location.getY())).replace("{pos_z}", String.valueOf(this.location.getZ()))));
                    }
                }
                else if(ChatUtil.getMessageType() == MessageType.TITLE) {
                    ChatUtil.title(user, ChatUtil.translate(ChatUtil.getMessage("rtp-success.rtp-success-message")), ChatUtil.translate(ChatUtil.getMessage("rtp-success.subtitle").replace("{pos_x}", String.valueOf(this.location.getX())).replace("{pos_y}", String.valueOf(this.location.getY())).replace("{pos_z}", String.valueOf(this.location.getZ()))), 20, 60, 20);
                }
                return RtpCallback.SUCCESS;
            case FAR:
                switch (senderType) {
                    case PLAYER:
                        if (!sender.hasPermission("elitertp.channel.far")) {
                            ChatUtil.messageTranslated(sender, "dont_have_permission");
                            return RtpCallback.DONT_HAVE_PERMISSION;
                        }
                    case ADMIN:
                        if (!sender.hasPermission("elitertp.channel.far.others")) {
                            ChatUtil.messageTranslated(sender, "dont_have_permission");
                            return RtpCallback.DONT_HAVE_PERMISSION;
                        }
                    case CONSOLE:
                        if (!sender.hasPermission("elitertp.channel.far.others")) {
                            ChatUtil.messageTranslated(sender, "dont_have_permission");
                            return RtpCallback.DONT_HAVE_PERMISSION;
                        }
                }
                if(hasCooldown(user.getPlayer().getUniqueId(), RtpType.FAR, ConfigUtils.getFarCooldown())) {
                    if(!user.getPlayer().hasPermission("elitertp.far.cooldown.bypass")) {
                        ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("player-has-cooldown").replace("{cooldown}", String.valueOf(ConfigUtils.getFarCooldown())).replace("{cooldown_remained}", String.valueOf(ConfigUtils.getFarCooldown() - getCooldown(user.getPlayer().getUniqueId(), RtpType.FAR)))));
                        return RtpCallback.COOLDOWN;
                    }
                }
                Player player2 = user.getPlayer();
                if(RtpUtils.isWorldBlocked(player2.getWorld())) {
                    ChatUtil.messageTranslated(player2, "world-is-blocked");
                    return RtpCallback.WORLD_BLOCKED;
                }
                this.location = RtpUtils.generateFarLocation(player2.getWorld());
                if(RtpUtils.isBlockBlocked(this.location.getBlock().getType())) {
                    tp();
                    return RtpCallback.SUCCESS;
                }
                if(player2.getFoodLevel() - ConfigUtils.getRemoveFood(RtpType.FAR) <= 0) {
                    ChatUtil.messageTranslated(sender, "not-enough-food");
                    return RtpCallback.NOT_ENOUGH_FOOD;
                }
                if(player2.getHealth() - ConfigUtils.getRemoveHeal(RtpType.FAR) <= 0) {
                    ChatUtil.messageTranslated(sender, "not-enough-heal");
                    return RtpCallback.NOT_ENOUGH_HEAL;
                }
                setCooldown(user.getPlayer().getUniqueId(), RtpType.FAR);
                player2.teleport(new Location(this.location.getWorld(), this.location.getX(), this.location.getY() + 1, this.location.getZ()));
                player2.setFoodLevel(player2.getFoodLevel() - ConfigUtils.getRemoveFood(RtpType.FAR));
                player2.setHealth(player2.getHealth() - ConfigUtils.getRemoveHeal(RtpType.FAR));
                EliteRtpPremium.getInstance().callEvent(new UserRtpEvent(sender, user, RtpType.FAR, senderType, location));
                if(ChatUtil.getMessageType() == MessageType.MESSAGE) {
                    if(senderType == SenderType.PLAYER) {
                        ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("rtp-success.rtp-success-message").replace("{pos_x}", String.valueOf(this.location.getX())).replace("{pos_y}", String.valueOf(this.location.getY())).replace("{pos_z}", String.valueOf(this.location.getZ()))));
                    }
                    else if(senderType == SenderType.ADMIN || senderType == SenderType.CONSOLE) {
                        ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("rtp-success.rtp-success-message").replace("{player_name}", user.getPlayer().getName()).replace("{pos_x}", String.valueOf(this.location.getX())).replace("{pos_y}", String.valueOf(this.location.getY())).replace("{pos_z}", String.valueOf(this.location.getZ()))));
                    }
                }
                else if(ChatUtil.getMessageType() == MessageType.TITLE) {
                    ChatUtil.title(user, ChatUtil.translate(ChatUtil.getMessage("rtp-success.rtp-success-message")), ChatUtil.translate(ChatUtil.getMessage("rtp-success.subtitle").replace("{pos_x}", String.valueOf(this.location.getX())).replace("{pos_y}", String.valueOf(this.location.getY())).replace("{pos_z}", String.valueOf(this.location.getZ()))), 20, 60, 20);
                }
                return RtpCallback.SUCCESS;
            case WORLD:
                switch (senderType) {
                    case PLAYER:
                        if (!sender.hasPermission("elitertp.channel.world")) {
                            ChatUtil.messageTranslated(sender, "dont_have_permission");
                            return RtpCallback.DONT_HAVE_PERMISSION;
                        }
                    case ADMIN:
                        if (!sender.hasPermission("elitertp.channel.world.others")) {
                            ChatUtil.messageTranslated(sender, "dont_have_permission");
                            return RtpCallback.DONT_HAVE_PERMISSION;
                        }
                    case CONSOLE:
                        if (!sender.hasPermission("elitertp.channel.world.others")) {
                            ChatUtil.messageTranslated(sender, "dont_have_permission");
                            return RtpCallback.DONT_HAVE_PERMISSION;
                        }
                }
                if(hasCooldown(user.getPlayer().getUniqueId(), RtpType.WORLD, ConfigUtils.getWorldCooldown())) {
                    if(!user.getPlayer().hasPermission("elitertp.world.cooldown.bypass")) {
                        ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("player-has-cooldown").replace("{cooldown}", String.valueOf(ConfigUtils.getWorldCooldown())).replace("{cooldown_remained}", String.valueOf(ConfigUtils.getWorldCooldown() - getCooldown(user.getPlayer().getUniqueId(), RtpType.WORLD)))));
                        return RtpCallback.COOLDOWN;
                    }
                }
                if(RtpUtils.isInWorldBlocked(world)) {
                    ChatUtil.messageTranslated(sender, "in-world-tp-blocked");
                    return RtpCallback.WORLD_IN_BLOCKED;
                }
                Player player3 = user.getPlayer();
                if(RtpUtils.isWorldBlocked(player3.getWorld())) {
                    ChatUtil.messageTranslated(player3, "world-is-blocked");
                    return RtpCallback.WORLD_BLOCKED;
                }
                this.location = RtpUtils.generateWorldLocation(player3.getWorld());
                if(RtpUtils.isBlockBlocked(this.location.getBlock().getType())) {
                    tp();
                    return RtpCallback.SUCCESS;
                }
                if(player3.getFoodLevel() - ConfigUtils.getRemoveFood(RtpType.WORLD) <= 0) {
                    ChatUtil.messageTranslated(sender, "not-enough-food");
                    return RtpCallback.NOT_ENOUGH_FOOD;
                }
                if(player3.getHealth() - ConfigUtils.getRemoveHeal(RtpType.WORLD) <= 0) {
                    ChatUtil.messageTranslated(sender, "not-enough-heal");
                    return RtpCallback.NOT_ENOUGH_HEAL;
                }
                setCooldown(user.getPlayer().getUniqueId(), RtpType.WORLD);
                player3.teleport(new Location(this.world, this.location.getX(), this.location.getY() + 1, this.location.getZ()));
                player3.setFoodLevel(player3.getFoodLevel() - ConfigUtils.getRemoveFood(RtpType.WORLD));
                player3.setHealth(player3.getHealth() - ConfigUtils.getRemoveHeal(RtpType.WORLD));
                EliteRtpPremium.getInstance().callEvent(new UserRtpEvent(sender, user, RtpType.WORLD, senderType, location, world));
                if(ChatUtil.getMessageType() == MessageType.MESSAGE) {
                    if(senderType == SenderType.PLAYER) {
                        ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("rtp-success.rtp-success-message").replace("{pos_x}", String.valueOf(this.location.getX())).replace("{pos_y}", String.valueOf(this.location.getY())).replace("{pos_z}", String.valueOf(this.location.getZ()))));
                    }
                    else if(senderType == SenderType.ADMIN || senderType == SenderType.CONSOLE) {
                        ChatUtil.message(sender, ChatUtil.translate(ChatUtil.getMessage("rtp-success.rtp-success-message").replace("{player_name}", user.getPlayer().getName()).replace("{pos_x}", String.valueOf(this.location.getX())).replace("{pos_y}", String.valueOf(this.location.getY())).replace("{pos_z}", String.valueOf(this.location.getZ()))));
                    }
                }
                else if(ChatUtil.getMessageType() == MessageType.TITLE) {
                    ChatUtil.title(user, ChatUtil.translate(ChatUtil.getMessage("rtp-success.rtp-success-message")), ChatUtil.translate(ChatUtil.getMessage("rtp-success.subtitle").replace("{pos_x}", String.valueOf(this.location.getX())).replace("{pos_y}", String.valueOf(this.location.getY())).replace("{pos_z}", String.valueOf(this.location.getZ()))), 20, 60, 20);
                }
                return RtpCallback.SUCCESS;
        }
        return RtpCallback.UNKNOWN_ERROR;
    }
}