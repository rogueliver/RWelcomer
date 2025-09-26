package me.rogueliver.rwelcomer.commands;

import me.rogueliver.rwelcomer.RWelcomer;
import me.rogueliver.rwelcomer.config.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigCommand implements CommandExecutor, TabCompleter {
    
    private final RWelcomer plugin;
    private final ConfigManager configManager;
    private final List<String> validSettings = Arrays.asList(
            "welcome-message", "reward-amount", "max-rewarded-players", "reload"
    );
    
    public ConfigCommand(RWelcomer plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("rwelcomer.admin")) {
            sender.sendMessage(configManager.getNoPermissionMessage());
            return true;
        }
        
        if (args.length == 0) {
            sender.sendMessage("§7Available settings: " + String.join(", ", validSettings));
            return true;
        }
        
        String setting = args[0].toLowerCase();
        
        if (setting.equals("reload")) {
            configManager.reloadConfig();
            sender.sendMessage(configManager.getConfigReloadedMessage());
            return true;
        }
        
        if (args.length < 2) {
            sender.sendMessage("§cPlease provide a value for the setting.");
            return true;
        }
        
        String value = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        
        switch (setting) {
            case "welcome-message":
                configManager.updateSetting("welcome.message", value);
                break;
            case "reward-amount":
                try {
                    double amount = Double.parseDouble(value);
                    configManager.updateSetting("greetings.reward-amount", amount);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§cInvalid number format!");
                    return true;
                }
                break;
            case "max-rewarded-players":
                try {
                    int maxPlayers = Integer.parseInt(value);
                    configManager.updateSetting("greetings.max-rewarded-players", maxPlayers);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§cInvalid number format!");
                    return true;
                }
                break;
            default:
                sender.sendMessage(configManager.getInvalidSettingMessage());
                return true;
        }
        
        String message = configManager.getSettingUpdatedMessage()
                .replace("{setting}", setting)
                .replace("{value}", value);
        sender.sendMessage(message);
        
        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return validSettings.stream()
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}