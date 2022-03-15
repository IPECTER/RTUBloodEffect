package com.github.ipecter.rtu.bloodeffect.util;

import com.github.ipecter.rtu.bloodeffect.Main;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ConfigManager {
    private Data data = Data.getInstance();
    private Plugin plugin = (Main) Main.getPlugin(Main.class);
    private ConfigManager() {
    }

    private static class LazyHolder {
        public static final ConfigManager INSTANCE = new ConfigManager();
    }

    public static ConfigManager getInstance() {
        return ConfigManager.LazyHolder.INSTANCE;
    }

    public void loadConfig() {
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdirs();
        File file = copyResource(plugin, "setting.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        data.setRhp(config.getBoolean("remove-hart-particle", true));
        data.setAccuracy(config.getDouble("accuracy", 0.5D));
        data.setAmount(config.getInt("amount", 15));

        File file2 = copyResource(plugin, "mobs.yml");
        YamlConfiguration config2 = YamlConfiguration.loadConfiguration(file2);
        if (config2.isConfigurationSection("mobs")) {
            ConfigurationSection section = config2.getConfigurationSection("mobs");
            for (String mobType : section.getKeys(false))
                data.mobList.put(mobType, Material.matchMaterial(section.getString(mobType)));
        }
        for (Player player : Bukkit.getOnlinePlayers()){
            loadPlayerData(player);
        }
    }
    public void loadPlayerData(Player player){
        File f = new File(plugin.getDataFolder() + "playerdata/");
        if (!f.exists())
            f.mkdirs();
        File file = copyResource(plugin, f + player.getUniqueId().toString() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        data.playerDataParticle.put(player, config.getBoolean("particle", true));
    }
    public void savePlayerData(Player player, boolean b){
        File f = new File(plugin.getDataFolder() + "playerdata/");
        if (!f.exists())
            f.mkdirs();
        File file = copyResource(plugin, f + player.getUniqueId().toString() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("particle", b);
        data.playerDataParticle.put(player, b);
    }
    private File copyResource(Plugin plugin, String resource) {
        File folder = plugin.getDataFolder();
        File resourceFile = new File(folder, resource);
        if (!resourceFile.exists()) {
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
        }
        return resourceFile;
    }

}
