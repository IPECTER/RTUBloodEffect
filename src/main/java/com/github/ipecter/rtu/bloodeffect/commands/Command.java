package com.github.ipecter.rtu.bloodeffect.commands;

import com.github.ipecter.rtu.bloodeffect.managers.ConfigManager;
import com.github.ipecter.rtu.utilapi.RTUUtilAPI;
import com.github.ipecter.rtu.utilapi.managers.TextManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Command implements CommandExecutor, TabCompleter {

    private ConfigManager configManager = ConfigManager.getInstance();
    private TextManager textManager = RTUUtilAPI.getTextManager();

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("rtube.reload")) {
                configManager.initConfigFiles();
                sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getPrefix() + configManager.getReloadMsg()));
            } else {
                sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getPrefix() + configManager.getNoPermission()));
            }
            return true;
        } else if (args.length >= 1 && args[0].equalsIgnoreCase("on")) {
            if (args.length >= 2 && Bukkit.getPlayer(args[1]) != null) {
                if (sender.hasPermission("rtube.toggle.other")) {
                    setStatus(Bukkit.getPlayer(args[1]), true);
                    sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getPrefix() + "&f" + args[1] + configManager.getBloodEffectON()));
                } else {
                    sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getPrefix() + configManager.getNoPermission()));
                }
            } else {
                if (sender instanceof Player) {
                    setStatus((Player) sender, true);
                    sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getPrefix() + configManager.getBloodEffectON()));
                } else {
                    sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getPrefix() + configManager.getCommandWrongUsageConsole()));
                }
            }
        } else {
            sender.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getPrefix() + configManager.getCommandWrongUsage()));
            return true;
        }
    }

    private void setStatus(Player player, boolean value) {
        RTUUtilAPI.getPermissionManager().setPerm(player, "rtube.onoff", value);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        if (args.length == 1 && sender.hasPermission("rtube.reload")) {
            return Arrays.asList("reload");
        }
        return Arrays.asList();
    }
}
