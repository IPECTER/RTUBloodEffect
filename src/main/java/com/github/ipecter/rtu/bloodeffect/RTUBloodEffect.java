package com.github.ipecter.rtu.bloodeffect;

import com.github.ipecter.rtu.bloodeffect.commands.Command;
import com.github.ipecter.rtu.bloodeffect.listeners.EntityDamageByEntity;
import com.github.ipecter.rtu.bloodeffect.listeners.PlayerJoin;
import com.github.ipecter.rtu.bloodeffect.listeners.ProjectileHit;
import com.github.ipecter.rtu.bloodeffect.managers.ConfigManager;
import com.github.ipecter.rtu.utilapi.RTUUtilAPI;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RTUBloodEffect extends JavaPlugin {

    private String prefix = IridiumColorAPI.process("<GRADIENT:a83232>[ RTUBloodEffect ]</GRADIENT:a3a3a3> ");

    @Override
    public void onEnable() {
        try {
            RTUUtilAPI.init(this);
            Bukkit.getLogger().info(RTUUtilAPI.getTextManager().formatted(prefix + "&aEnable&f!"));
            ConfigManager.getInstance().initConfigFiles();
            registerEvent();
            setExecutor();
            loadDependencies();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(RTUUtilAPI.getTextManager().formatted(prefix + "&cDisable&f!"));
    }

    protected void registerEvent() {
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntity(), this);
        Bukkit.getPluginManager().registerEvents(new ProjectileHit(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    protected void setExecutor() {
        getCommand("rtucc").setExecutor(new Command());
    }

    private void loadDependencies() {
        loadProtocolLib();
        loadPAPI();
    }


    private void loadProtocolLib() {
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            RTUUtilAPI.getDependencyManager().setUseProtocolLib(true);
        }
    }

    private void loadPAPI() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            RTUUtilAPI.getDependencyManager().setUsePAPI(true);
        }
    }
}
