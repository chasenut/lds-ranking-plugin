package me.Cashtann.combatRankingSystem.ranking;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class RatingController {

    public static int getPlayerCombatRating(Player player) {
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        if (playerData.has(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER)) {
            return playerData.get(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER);
        } else {
            return 0;
        }
    }

    public static void setPlayerCombatRating(Player player, int newRating) {
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        if (newRating >= 0 && playerData.has(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER)) {
            playerData.set(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER, newRating);
        }
    }
}
