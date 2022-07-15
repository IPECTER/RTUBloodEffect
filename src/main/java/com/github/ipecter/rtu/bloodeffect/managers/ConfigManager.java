package com.github.ipecter.rtu.bloodeffect.managers;

import com.github.ipecter.rtu.bloodeffect.RTUBloodEffect;
import com.github.ipecter.rtu.utilapi.RTUUtilAPI;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private Plugin plugin = RTUBloodEffect.getPlugin(RTUBloodEffect.class);
    private boolean enablePlugin = true;
    private boolean motd = true;
    private boolean particleDisableVanillaDamage = true;
    private int amount = 0;
    private double accuracy = 0.5;
    private Material defaultMaterial = Material.REDSTONE;
    private String locale = "EN";
    private Map<String, String> mobMaterial = Collections.synchronizedMap(new HashMap<>());
    private String prefix = IridiumColorAPI.process("<GRADIENT:a83232>[ RTUBloodEffect ]</GRADIENT:a3a3a3> ");
    private String reloadMsg = "";
    private String commandWrongUsage = "";
    private String commandWrongUsageOp = "";
    private String noPermission = "";
    private String bloodEffectON = "";
    private String bloodEffectOFF = "";

    public ConfigManager() {
    }

    public final static ConfigManager getInstance() {
        return ConfigManager.InnerInstanceClass.instance;
    }

    public boolean isEnablePlugin() {
        return enablePlugin;
    }

    public void setEnablePlugin(boolean enablePlugin) {
        this.enablePlugin = enablePlugin;
    }

    public boolean isMotd() {
        return motd;
    }

    public void setMotd(boolean motd) {
        this.motd = motd;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public Material getDefaultMaterial() {
        return defaultMaterial;
    }

    public void setDefaultMaterial(Material defaultMaterial) {
        this.defaultMaterial = defaultMaterial;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Map<String, String> getMobMaterial() {
        return mobMaterial;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getReloadMsg() {
        return reloadMsg;
    }

    public void setReloadMsg(String reloadMsg) {
        this.reloadMsg = reloadMsg;
    }

    public String getCommandWrongUsage() {
        return commandWrongUsage;
    }

    public void setCommandWrongUsage(String commandWrongUsage) {
        this.commandWrongUsage = commandWrongUsage;
    }

    public String getCommandWrongUsageOp() {
        return commandWrongUsageOp;
    }

    public void setCommandWrongUsageOp(String commandWrongUsageOp) {
        this.commandWrongUsageOp = commandWrongUsageOp;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public void setNoPermission(String noPermission) {
        this.noPermission = noPermission;
    }

    public String getBloodEffectON() {
        return bloodEffectON;
    }

    public void setBloodEffectON(String bloodEffectON) {
        this.bloodEffectON = bloodEffectON;
    }

    public String getBloodEffectOFF() {
        return bloodEffectOFF;
    }

    public void setBloodEffectOFF(String bloodEffectOFF) {
        this.bloodEffectOFF = bloodEffectOFF;
    }

    public void initConfigFiles() {
        initSetting(RTUUtilAPI.getFileManager().copyResource("Setting.yml"));
        initMessage(RTUUtilAPI.getFileManager().copyResource("Translations", "Locale_" + locale + ".yml"));
        initMobMaterial(RTUUtilAPI.getFileManager().copyResource("MobMaterial.yml"));
    }

    private void initSetting(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        enablePlugin = config.getBoolean("enablePlugin");
        motd = config.getBoolean("motd");
        locale = config.getString("locale");
        particleDisableVanillaDamage = config.getBoolean("particle.disableVanillaDamage");
        Material material = Material.getMaterial(config.getString("particle.defaultMaterial"));
        defaultMaterial = material != null ? material : Material.REDSTONE;
    }

    private void initMessage(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        prefix = config.getString("prefix", "").isEmpty() ? prefix : config.getString("prefix");
        reloadMsg = config.getString("reloadMsg");
        commandWrongUsage = config.getString("commandWrongUsage");
        commandWrongUsageOp = config.getString("commandWrongUsageOp");
        noPermission = config.getString("noPermission");
        bloodEffectON = config.getString("bloodEffectON");
        bloodEffectOFF = config.getString("bloodEffectOFF");

        RTUUtilAPI.getFileManager().copyResource("Translations", "Locale_EN.yml");
        RTUUtilAPI.getFileManager().copyResource("Translations", "Locale_KR.yml");
    }

    private void initMobMaterial(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String group : config.getConfigurationSection("list").getKeys(false)) {
            mobMaterial.put(group, config.getConfigurationSection("list").getString("." + group));
        }
    }

    private static class InnerInstanceClass {
        private static final ConfigManager instance = new ConfigManager();
    }
}
