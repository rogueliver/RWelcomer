package me.rogueliver.rwelcomer.managers;

import lombok.Getter;
import me.rogueliver.rwelcomer.RWelcomer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

@Getter
public class VaultManager {
    
    private final RWelcomer plugin;
    private Economy economy;
    
    public VaultManager(RWelcomer plugin) {
        this.plugin = plugin;
    }
    
    public boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        
        economy = rsp.getProvider();
        return economy != null;
    }
    
    public void depositPlayer(Player player, double amount) {
        if (economy != null) {
            economy.depositPlayer(player, amount);
        }
    }
    
    public boolean hasEconomy() {
        return economy != null;
    }
}