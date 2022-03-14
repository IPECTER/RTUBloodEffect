package com.github.ipecter.rtu.bloodeffect;

import com.github.ipecter.rtu.bloodeffect.listeners.EntityDamageByEntityEvent;
import com.github.ipecter.rtu.bloodeffect.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private ConfigManager configManager = new ConfigManager((Plugin) this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents((Listener)new EntityDamageByEntityEvent(this.configManager), (Plugin)this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
