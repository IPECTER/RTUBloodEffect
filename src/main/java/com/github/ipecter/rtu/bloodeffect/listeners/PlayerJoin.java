package com.github.ipecter.rtu.bloodeffect.listeners;

import com.github.ipecter.rtu.bloodeffect.util.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private ConfigManager cm = ConfigManager.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        cm.loadPlayerData(e.getPlayer());
    }
}
