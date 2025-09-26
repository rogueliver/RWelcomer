package me.rogueliver.rwelcomer.listeners;

import me.rogueliver.rwelcomer.RWelcomer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    
    private final RWelcomer plugin;
    
    public PlayerJoinListener(RWelcomer plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!plugin.getConfigManager().isWelcomeEnabled()) {
            return;
        }
        
        Player player = event.getPlayer();
        
        if (!player.hasPlayedBefore()) {
            String welcomeMessage = plugin.getConfigManager().getWelcomeMessage()
                    .replace("{player}", player.getName());
            
            if (plugin.getConfigManager().isBroadcastEnabled()) {
                plugin.getServer().broadcastMessage(welcomeMessage);
            } else {
                player.sendMessage(welcomeMessage);
            }
        }
    }
}