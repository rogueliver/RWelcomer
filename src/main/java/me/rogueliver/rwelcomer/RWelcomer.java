package me.rogueliver.rwelcomer;

import lombok.Getter;
import me.rogueliver.rwelcomer.commands.ConfigCommand;
import me.rogueliver.rwelcomer.config.ConfigManager;
import me.rogueliver.rwelcomer.listeners.ChatListener;
import me.rogueliver.rwelcomer.listeners.PlayerJoinListener;
import me.rogueliver.rwelcomer.managers.VaultManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class RWelcomer extends JavaPlugin {
    
    private ConfigManager configManager;
    private VaultManager vaultManager;
    
    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.vaultManager = new VaultManager(this);
        
        if (!vaultManager.setupEconomy()) {
            getLogger().severe("Vault not found! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        registerListeners();
        registerCommands();

        getLogger().info("RWelcomer has been enabled!" +
                " Using Vault version: " + vaultManager.getEconomy().getName());
        getLogger().info("Made by RogueLiver. For support, visit: github.com/rogueliver");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("RWelcomer has been disabled!");
    }
    
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
    }
    
    private void registerCommands() {
        getCommand("rwelcomer").setExecutor(new ConfigCommand(this));
    }
}