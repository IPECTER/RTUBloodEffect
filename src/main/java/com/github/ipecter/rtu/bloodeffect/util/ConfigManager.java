package com.github.ipecter.rtu.bloodeffect.util;

import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ConfigManager {
    private final Plugin plugin;

    private Data data = Data.getInstance();
    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdirs();
        loadConfig();
    }

    public void loadConfig() {
        File file = copyResource(this.plugin, "setting.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        data.setRhp(config.getBoolean("remove-hart-particle", true));
        data.setAccuracy(config.getDouble("accuracy", 0.5D));
        data.setAmount(config.getInt("amount", 15));
        File file2 = copyResource(this.plugin, "mobs.yml");
        YamlConfiguration config2 = YamlConfiguration.loadConfiguration(file2);
        if (config2.isConfigurationSection("mobs")) {
            ConfigurationSection section = config2.getConfigurationSection("mobs");
            for (String mobType : section.getKeys(false))
                data.mobList.put(mobType, Material.matchMaterial(section.getString(mobType)));
        }
    }

    private File copyResource(Plugin plugin, String resource) {
        File folder = plugin.getDataFolder();
        File resourceFile = new File(folder, resource);
        if (!resourceFile.exists())
            try {
                resourceFile.createNewFile();
                InputStream in = plugin.getResource(resource);
                try {
                    OutputStream out = new FileOutputStream(resourceFile);
                    try {
                        ByteStreams.copy(in, out);
                        out.close();
                    } catch (Throwable throwable) {
                        try {
                            out.close();
                        } catch (Throwable throwable1) {
                            throwable.addSuppressed(throwable1);
                        }
                        throw throwable;
                    }
                    if (in != null)
                        in.close();
                } catch (Throwable throwable) {
                    if (in != null)
                        try {
                            in.close();
                        } catch (Throwable throwable1) {
                            throwable.addSuppressed(throwable1);
                        }
                    throw throwable;
                }
            } catch (Exception e) {
                Bukkit.getLogger().severe("Error copying file " + resource);
            }
        return resourceFile;
    }
    private double accuracy;

    private int amount;

    private boolean rhp;

    public double getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isRhp() {
        return this.rhp;
    }

    public void setRhp(boolean rhp) {
        this.rhp = rhp;
    }
}
