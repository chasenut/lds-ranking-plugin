package me.Cashtann.combatRankingSystem.listeners;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PersistentDataContainer playerData = player.getPersistentDataContainer();
        if (!playerData.has(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER)) {
            int initialCombatRating = CombatRankingSystem.getPlugin().getConfig().getInt("initial-combat-rating");
            playerData.set(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER, initialCombatRating);
        } else {
            CombatRankingSystem.getPlugin().getLogger().info("Player who joined already has combat_rating: ");
            int playerRating = playerData.get(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER);
            CombatRankingSystem.getPlugin().getLogger().info(String.valueOf(playerRating));
        }
    }
}
