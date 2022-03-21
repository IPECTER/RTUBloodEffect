package com.github.ipecter.rtu.bloodeffect.util;

import com.github.ipecter.rtu.bloodeffect.RTUBloodEffect;
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
import java.sql.*;

public class FileManager {
    private Data data = Data.getInstance();
    private Plugin plugin = (RTUBloodEffect) RTUBloodEffect.getPlugin(RTUBloodEffect.class);
    private FileManager() {
    }

    private static class LazyHolder {
        public static final FileManager INSTANCE = new FileManager();
    }

    public static FileManager getInstance() {
        return FileManager.LazyHolder.INSTANCE;
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

    private  Connection connect() {
        String url = "jdbc:sqlite:" + plugin.getDataFolder() + File.separator + "database.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void sendStatement(String sql) {
        sendPreparedStatement(sql, null);
    }

    private void sendPreparedStatement(String sql, Object... parameters) {
        sendQueryStatement(sql, null, parameters);
    }

    private Object sendQueryStatement(String sql, String query, Object... parameters) {
        Object result = null;

        try {
            try ( Connection conn = connect()) {
                if ((parameters == null || parameters.length == 0) && query == null) {
                    //Simple statement
                    Statement statement = conn.createStatement();
                    statement.execute(sql);
                } else {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    for (int i = 0; i < parameters.length; i++) {
                        ps.setObject(i + 1, parameters[i]);
                    }

                    if (query == null) {
                        //Prepared statement
                        ps.execute();
                    } else {
                        //Query statement
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            result = rs.getObject(query);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void createTables() {
        String sql = "CREATE TABLE IF NOT EXISTS rtube ("
                + "id integer PRIMARY KEY AUTOINCREMENT, "
                + "player text NOT NULL UNIQUE, "
                + "toggle tinyint NOT NULL);";

        sendStatement(sql);
    }

    public void initData(String playerName) {
        String sql = "INSERT OR IGNORE INTO rtube(player, toggle) VALUES(?, ?)";
        sendPreparedStatement(sql, playerName, 1);
    }

    public boolean getToggleStatus(String playerName) {
        initData(playerName);
        boolean status = false;

        String sql = "SELECT toggle FROM rtube WHERE player = ?";
        Object toggleInt = sendQueryStatement(sql, "toggle", playerName);
        if (toggleInt != null && toggleInt instanceof Integer) {
            status = (Integer) toggleInt == 1;
        }
        return status;
    }

    public void setToggleStatus(String playerName, boolean status) {
        initData(playerName);
        int toggleInt = status ? 1 : 0;

        String sql = "UPDATE rtube SET toggle = ? WHERE player = ?";
        sendPreparedStatement(sql, toggleInt, playerName);
    }
}
