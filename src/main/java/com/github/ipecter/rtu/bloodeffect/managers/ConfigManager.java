package com.github.ipecter.rtu.bloodeffect.managers;

import com.github.ipecter.rtu.bloodeffect.RTUBloodEffect;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    public final static ConfigManager getInstance() {
        return ConfigManager.InnerInstanceClass.instance;
    }

    private static class InnerInstanceClass {
        private static final ConfigManager instance = new ConfigManager();
    }

    @Getter
    @Setter
    private boolean enablePlugin = true;

    @Getter
    @Setter
    private boolean motd = true;

    @Getter
    @Setter
    private boolean particleDisableVanillaDamage = true;

    @Getter
    @Setter
    private int amount = 0;

    @Getter
    @Setter
    private double accuracy = 0.5;

    @Getter
    @Setter
    private Material defaultMaterial = Material.REDSTONE_BLOCK;

    @Getter
    @Setter
    private boolean spawnParticleGeneral = true;

    @Getter
    @Setter
    private String locale = "EN";

    @Getter
    @Setter
    private Map<String, String> mobMaterial = Collections.synchronizedMap(new HashMap<>());

    private void initSetting(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        enablePlugin = config.getBoolean("enablePlugin");
        motd = config.getBoolean("motd");
        locale = config.getString("locale");
        particleDisableVanillaDamage = config.getBoolean("particle.disableVanillaDamage");
        Material material = Material.getMaterial(config.getString("particle.defaultMaterial"));
        defaultMaterial = material != null ? material : Material.REDSTONE;
        spawnParticleGeneral = config.getBoolean("spawnParticleGeneral", spawnParticleGeneral);
        accuracy = config.getDouble("particle.accuracy");
        amount = config.getInt("particle.amount");
    }

    public void initConfigFiles() {
        initSetting(RTUPluginLib.getFileManager().copyResource("Setting.yml"));
        initMessage(RTUPluginLib.getFileManager().copyResource("Translations", "Locale_" + locale + ".yml"));
        initMobMaterial(RTUPluginLib.getFileManager().copyResource("MobMaterial.yml"));
    }

    private void initMessage(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        msgKeyMap.clear();
        for (String key : config.getKeys(false)) {
            if (key.equals("prefix")) {
                String prefixText = config.getString("prefix", "");
                msgKeyMap.put(key, prefixText.isEmpty() ? MiniMessage.miniMessage().serialize(RTUBloodEffect.prefix) : prefixText);
            } else {
                msgKeyMap.put(key, config.getString(key));
            }
        }
        RTUPluginLib.getFileManager().copyResource("Translations", "Locale_EN.yml");
        RTUPluginLib.getFileManager().copyResource("Translations", "Locale_KR.yml");
    }

    private void initMobMaterial(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (config.getConfigurationSection("list") != null) {
            mobMaterial.clear();
            for (String group : config.getConfigurationSection("list").getKeys(false)) {
                mobMaterial.put(group, config.getConfigurationSection("list").getString("." + group));
            }
        }
    }

    private final Map<String, String> msgKeyMap = Collections.synchronizedMap(new HashMap<>());

    public String getTranslation(String key) {
        return msgKeyMap.getOrDefault(key, "");
    }
}
