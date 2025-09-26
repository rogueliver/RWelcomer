package me.rogueliver.rwelcomer.listeners;

import me.rogueliver.rwelcomer.RWelcomer;
import me.rogueliver.rwelcomer.config.ConfigManager;
import me.rogueliver.rwelcomer.managers.VaultManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatListener implements Listener {
    
    private final RWelcomer plugin;
    private final ConfigManager configManager;
    private final VaultManager vaultManager;
    private final Pattern helloPattern;
    
    public ChatListener(RWelcomer plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.vaultManager = plugin.getVaultManager();
        this.helloPattern = Pattern.compile("^hello\\s+(\\w+)$", Pattern.CASE_INSENSITIVE);
    }
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (!configManager.isGreetingEnabled()) {
            return;
        }
        
        Player player = event.getPlayer();
        String message = event.getMessage().trim();
        
        Matcher matcher = helloPattern.matcher(message);
        if (!matcher.matches()) {
            return;
        }
        
        String targetPlayerName = matcher.group(1);
        
        if (player.getName().equalsIgnoreCase(targetPlayerName)) {
            return;
        }
        
        Player targetPlayer = plugin.getServer().getPlayer(targetPlayerName);
        if (targetPlayer == null || !targetPlayer.isOnline()) {
            return;
        }
        
        if (configManager.hasReachedMaxRewards()) {
            player.sendMessage(configManager.getMaxRewardsReachedMessage());
            return;
        }
        
        if (configManager.hasPlayerBeenRewarded(player.getName())) {
            player.sendMessage(configManager.getAlreadyRewardedMessage());
            return;
        }
        
        double rewardAmount = configManager.getRewardAmount();
        vaultManager.depositPlayer(player, rewardAmount);
        configManager.addRewardedPlayer(player.getName());
        
        String rewardMessage = configManager.getRewardMessage()
                .replace("{amount}", String.valueOf((int) rewardAmount))
                .replace("{target}", targetPlayer.getName());
        
        player.sendMessage(rewardMessage);
    }
}