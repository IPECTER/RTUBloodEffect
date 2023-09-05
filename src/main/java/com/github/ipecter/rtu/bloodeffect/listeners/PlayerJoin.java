package com.github.ipecter.rtu.bloodeffect.listeners;

import com.github.ipecter.rtu.bloodeffect.managers.ConfigManager;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private ConfigManager configManager = ConfigManager.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!configManager.isEnablePlugin()) return;
        Player player = e.getPlayer();
        if (configManager.isMotd()) {
            ((Audience) player).sendMessage(RTUPluginLib.getTextManager().formatted(player, configManager.getTranslation("prefix") + "<white>RTU BloodEffect developed by IPECTER & Mkkas3145 (Original)"));
        } else {
            if (player.isOp())
                ((Audience) player).sendMessage(RTUPluginLib.getTextManager().formatted(player, configManager.getTranslation("prefix") + "&fRTU BloodEffect developed by IPECTER & Mkkas3145 (Original)"));
        }
    }
}
