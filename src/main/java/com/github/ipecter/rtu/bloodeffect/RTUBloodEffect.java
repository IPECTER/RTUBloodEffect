package com.github.ipecter.rtu.bloodeffect;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.github.ipecter.rtu.bloodeffect.commands.Command;
import com.github.ipecter.rtu.bloodeffect.listeners.EntityDamageByEntity;
import com.github.ipecter.rtu.bloodeffect.listeners.PlayerJoin;
import com.github.ipecter.rtu.bloodeffect.listeners.ProjectileHit;
import com.github.ipecter.rtu.bloodeffect.managers.ConfigManager;
import com.github.ipecter.rtu.bloodeffect.packet.BloodHeartParticle;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import com.github.ipecter.rtu.pluginlib.managers.TextManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RTUBloodEffect extends JavaPlugin {

    public static final Component prefix = RTUPluginLib.getTextManager().colored("<gradient:#cc1f1f:#a3a3a3>[ RTUBloodEffect ]</gradient> ");
    private final TextManager textManager = RTUPluginLib.getTextManager();


    @Override
    public void onEnable() {
        try {
            RTUPluginLib.init(this);
            Bukkit.getLogger().info(textManager.toString(prefix.append(textManager.colored("<green>Enable</green>!"))));
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
        Bukkit.getLogger().info(textManager.toString(prefix.append(textManager.colored("<red>Disable</red>!"))));
    }

    protected void registerEvent() {
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntity(), this);
        Bukkit.getPluginManager().registerEvents(new ProjectileHit(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    private ProtocolManager protocolManager;

    private void loadDependencies() {
        loadProtocolLib();
        loadPAPI();
    }

    protected void setExecutor() {
        getCommand("rtube").setExecutor(new Command());
    }

    private void loadProtocolLib() {
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            RTUPluginLib.getDependencyManager().setUseProtocolLib(true);
            protocolManager = ProtocolLibrary.getProtocolManager();
            protocolManager.addPacketListener(new BloodHeartParticle(this));
        }
    }

    private void loadPAPI() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            RTUPluginLib.getDependencyManager().setUsePAPI(true);
        }
    }
}
