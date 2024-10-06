package ru.korshun.elitertppremium.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.korshun.elitertppremium.api.rtp.Rtp;
import ru.korshun.elitertppremium.api.user.User;

import java.util.UUID;

public class EUser implements User {
    private UUID uuid;
    private Rtp rtp;
    private Player player;

    public EUser(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public Rtp rtp(Rtp rtp) {
        this.rtp = rtp;
        return rtp;
    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
