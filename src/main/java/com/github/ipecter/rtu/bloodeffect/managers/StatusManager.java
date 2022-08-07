package com.github.ipecter.rtu.bloodeffect.managers;

import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatusManager {

    private Map<UUID, Boolean> statusMap = Collections.synchronizedMap(new HashMap<>());

    public StatusManager() {
    }

    public final static StatusManager getInstance() {
        return StatusManager.InnerInstanceClass.instance;
    }

    public void setStatus(Player player, boolean value) {
        statusMap.put(player.getUniqueId(), value);
    }

    public boolean getStatus(Player player) {
        return statusMap.get(player.getUniqueId());
    }

    private static class InnerInstanceClass {
        private static final StatusManager instance = new StatusManager();
    }

}
