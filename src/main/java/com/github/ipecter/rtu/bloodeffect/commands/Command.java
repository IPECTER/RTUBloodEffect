package com.github.ipecter.rtu.bloodeffect.commands;

import com.github.ipecter.rtu.bloodeffect.managers.BloodStatusManager;
import com.github.ipecter.rtu.bloodeffect.managers.ConfigManager;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import com.github.ipecter.rtu.pluginlib.managers.TextManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Command implements CommandExecutor, TabCompleter {

    private ConfigManager configManager = ConfigManager.getInstance();
    private TextManager textManager = RTUPluginLib.getTextManager();

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("rtube.reload")) {
                configManager.initConfigFiles();
                ((Audience) sender).sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("reloadMsg")));
            } else {
                ((Audience) sender).sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("noPermission")));
            }
            return true;
        } else if (args.length >= 1 && args[0].equalsIgnoreCase("on")) {
            if (args.length >= 2 && Bukkit.getPlayer(args[1]) != null) {
                if (sender.hasPermission("rtube.toggle.other")) {
                    setStatus(Bukkit.getPlayer(args[1]), true);
                    ((Audience) sender).sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + "&f" + args[1] + configManager.getTranslation("bloodEffectOtherOn")));
                } else {
                    ((Audience) sender).sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("noPermission")));
                }
            } else {
                if (sender instanceof Player) {
                    setStatus((Player) sender, true);
                    ((Audience) sender).sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("bloodEffectOn")));
                } else {
                    ((Audience) sender).sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("commandWrongUsageConsole")));
                }
            }
            return true;
        } else if (args.length >= 1 && args[0].equalsIgnoreCase("off")) {
            if (args.length >= 2 && Bukkit.getPlayer(args[1]) != null) {
                if (sender.hasPermission("rtube.toggle.other")) {
                    setStatus(Bukkit.getPlayer(args[1]), false);
                    ((Audience) sender).sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + "&f" + args[1] + configManager.getTranslation("bloodEffectOtherOff")));
                } else {
                    ((Audience) sender).sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("noPermission")));
                }
            } else {
                if (sender instanceof Player) {
                    setStatus((Player) sender, false);
                    ((Audience) sender).sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("bloodEffectOff")));
                } else {
                    ((Audience) sender).sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("commandWrongUsageConsole")));
                }
            }
            return true;
        } else {
            if (sender.hasPermission("rtube.reload")) {
                ((Audience) sender).sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("commandWrongUsageOp")));

            } else {
                ((Audience) sender).sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("commandWrongUsage")));
            }
            return true;
        }
    }

    private void setStatus(Player player, boolean value) {
        BloodStatusManager.getInstance().setStatus(player, value);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            if (sender.hasPermission("rtube.reload")) {
                list.add("reload");
            }
            if (sender.hasPermission("rtube.toggle")) {
                list.add("on");
                list.add("off");
            }
            return list;
        } else if (args.length == 2) {
            if (sender.hasPermission("rtupd.toggle.other")) {
                return Bukkit.getOnlinePlayers().stream().map(player -> player.getName()).collect(Collectors.toList());
            }
        }
        return Arrays.asList();
    }
}
