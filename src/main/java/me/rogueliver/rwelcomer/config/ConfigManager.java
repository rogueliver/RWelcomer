package me.rogueliver.rwelcomer.config;

import lombok.Getter;
import me.rogueliver.rwelcomer.RWelcomer;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ConfigManager {
    
    private final RWelcomer plugin;
    private final Set<String> rewardedPlayers;
    
    public ConfigManager(RWelcomer plugin) {
        this.plugin = plugin;
        this.rewardedPlayers = new HashSet<>();
        plugin.saveDefaultConfig();
    }
    
    public void reloadConfig() {
        plugin.reloadConfig();
        rewardedPlayers.clear();
    }
    
    public boolean isWelcomeEnabled() {
        return getConfig().getBoolean("welcome.enabled", true);
    }
    
    public String getWelcomeMessage() {
        return colorize(getConfig().getString("welcome.message", "&a&lWelcome &f{player} &a&lto the server!"));
    }
    
    public boolean isBroadcastEnabled() {
        return getConfig().getBoolean("welcome.broadcast", true);
    }
    
    public boolean isGreetingEnabled() {
        return getConfig().getBoolean("greetings.enabled", true);
    }
    
    public double getRewardAmount() {
        return getConfig().getDouble("greetings.reward-amount", 5000.0);
    }
    
    public int getMaxRewardedPlayers() {
        return getConfig().getInt("greetings.max-rewarded-players", 3);
    }
    
    public String getRewardMessage() {
        return colorize(getConfig().getString("greetings.reward-message", "&a&l+${amount} &ffor welcoming &a{target}!"));
    }
    
    public String getAlreadyRewardedMessage() {
        return colorize(getConfig().getString("greetings.already-rewarded-message", "&cYou have already been rewarded for welcoming someone!"));
    }
    
    public String getMaxRewardsReachedMessage() {
        return colorize(getConfig().getString("greetings.max-rewards-reached-message", "&cThe maximum number of welcome rewards has been reached!"));
    }
    
    public String getConfigReloadedMessage() {
        return colorize(getConfig().getString("messages.config-reloaded", "&aConfiguration reloaded!"));
    }
    
    public String getInvalidSettingMessage() {
        return colorize(getConfig().getString("messages.invalid-setting", "&cInvalid setting! Available: welcome-message, reward-amount, max-rewarded-players"));
    }
    
    public String getSettingUpdatedMessage() {
        return colorize(getConfig().getString("messages.setting-updated", "&aSetting &f{setting} &aupdated to &f{value}"));
    }
    
    public String getNoPermissionMessage() {
        return colorize(getConfig().getString("messages.no-permission", "&cYou don't have permission to use this command!"));
    }
    
    public void updateSetting(String setting, Object value) {
        getConfig().set(setting, value);
        plugin.saveConfig();
    }
    
    public boolean hasReachedMaxRewards() {
        return rewardedPlayers.size() >= getMaxRewardedPlayers();
    }
    
    public boolean hasPlayerBeenRewarded(String playerName) {
        return rewardedPlayers.contains(playerName.toLowerCase());
    }
    
    public void addRewardedPlayer(String playerName) {
        rewardedPlayers.add(playerName.toLowerCase());
    }
    
    private FileConfiguration getConfig() {
        return plugin.getConfig();
    }
    
    private String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}