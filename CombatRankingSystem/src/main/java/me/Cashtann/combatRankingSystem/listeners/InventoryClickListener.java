package me.Cashtann.combatRankingSystem.listeners;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.utilities.StringFormatter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private final CombatRankingSystem plugin;
    private final String inventoryTitle;

    public InventoryClickListener(CombatRankingSystem plugin) {
        this.plugin = plugin;
        inventoryTitle = StringFormatter.formatString(this.plugin.getConfig().getString("topplayers.inventory-name"));
    }

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent event) {

        if (event.getView().getTitle().equals(inventoryTitle)) {
            event.setCancelled(true);
        }
    }
}
