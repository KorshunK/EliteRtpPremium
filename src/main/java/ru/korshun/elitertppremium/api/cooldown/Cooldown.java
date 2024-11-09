package ru.korshun.elitertppremium.api.cooldown;

import ru.korshun.elitertppremium.api.rtp.Rtp;
import ru.korshun.elitertppremium.api.rtp.RtpType;

import java.util.HashMap;
import java.util.UUID;

public class Cooldown {
    private static final HashMap<HashMap<UUID, RtpType>, Long> cooldown = new HashMap<>();

    public void setCooldown(UUID uuid, RtpType rtpType) {
        HashMap<UUID, RtpType> h = new HashMap<>();
        h.put(uuid, rtpType);
        cooldown.put(h, System.currentTimeMillis());
    }

    public Long getCooldown(UUID uuid, RtpType rtpType) {
        HashMap<UUID, RtpType> h = new HashMap<>();
        h.put(uuid, rtpType);
        return (System.currentTimeMillis() - cooldown.get(h)) / 1000;
    }



    // time - в секундах
    public boolean hasCooldown(UUID uuid, RtpType rtpType, Integer time) {
        HashMap<UUID, RtpType> h = new HashMap<>();
        h.put(uuid, rtpType);
        if(cooldown.containsKey(h)) {
            return getCooldown(uuid, rtpType) <= time;
        }
        return false;
    }
}