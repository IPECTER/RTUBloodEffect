package com.github.ipecter.rtu.bloodeffect;

import com.github.ipecter.rtu.bloodeffect.commands.ReloadCommand;
import com.github.ipecter.rtu.bloodeffect.listeners.EntityDamageByEntity;
import com.github.ipecter.rtu.bloodeffect.listeners.ProjectileHit;
import com.github.ipecter.rtu.bloodeffect.util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginCommand("rtube").setExecutor(new ReloadCommand(this));
        ConfigManager.getInstance().loadConfig(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntity(), this);
        Bukkit.getPluginManager().registerEvents(new ProjectileHit(), this);
    }
}
