package com.github.ipecter.rtu.bloodeffect.commands;

import com.github.ipecter.rtu.bloodeffect.util.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class ReloadCommand implements CommandExecutor, TabCompleter {
    private Plugin plugin;
    public ReloadCommand(Plugin plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[ &b&lRTU &6&lBE &f] &c/rtube reload"));
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("rtube.reload")) {
                new ConfigManager(plugin);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[ &b&lRTU &6&lBE &f] &aComplete reload config"));
                return true;
            }
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[ &b&lRTU &6&lBE &f] &cYou dont have permission"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1 &&
                sender.hasPermission("rtube.reload")) {
            return Arrays.asList("reload");
        }
        return Arrays.asList();
    }
}
