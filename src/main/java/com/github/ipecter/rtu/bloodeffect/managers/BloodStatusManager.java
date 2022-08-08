package com.github.ipecter.rtu.bloodeffect.managers;

import com.github.ipecter.rtu.utilapi.RTUUtilAPI;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BloodStatusManager {

    private final Map<UUID, Boolean> playerCache = Collections.synchronizedMap(new HashMap<>());

    public BloodStatusManager() {
    }

    public final static BloodStatusManager getInstance() {
        return BloodStatusManager.BloodStatusManagerClass.instance;
    }

    public boolean getStatus(Player player) {
        UUID uuid = player.getUniqueId();
        Boolean result = playerCache.get(uuid);
        if (result == null) {
            String value = RTUUtilAPI.getStatusManager().getStatus(player, "status");
            result = value != null ? Boolean.valueOf(value) : Boolean.TRUE;
            playerCache.put(uuid, result);
        }
        return result;
    }

    public void setStatus(Player player, boolean value) {
        RTUUtilAPI.getStatusManager().setStatus(player, "status", value);
        playerCache.put(player.getUniqueId(), value);
    }

    private static class BloodStatusManagerClass {
        private static final BloodStatusManager instance = new BloodStatusManager();
    }

}
