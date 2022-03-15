package com.github.ipecter.rtu.bloodeffect.commands;

import com.github.ipecter.rtu.bloodeffect.util.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {
    private ConfigManager cm = ConfigManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("rtube.reload")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[ &b&lRTU &6&lBE &f] &c/rtube on/off - &7Enable or Disable Particle"));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[ &b&lRTU &6&lBE &f] &c/rtube on/off"));
            }
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("rtube.reload")) {
                    ConfigManager.getInstance().loadConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[ &b&lRTU &6&lBE &f] &aComplete reload config"));
                    return true;
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[ &b&lRTU &6&lBE &f] &cYou dont have permission"));
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("on")) {
                if (sender instanceof Player) {
                    cm.savePlayerData((Player) sender, true);
                } else {

                }
            } else if (args[0].equalsIgnoreCase("off")) {
                if (sender instanceof Player) {
                    cm.savePlayerData((Player) sender, false);
                } else {

                }
            }
        }
        if (args.length == 2) {

        }

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
