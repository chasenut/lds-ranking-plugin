package me.Cashtann.combatRankingSystem.ranking;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;

import java.util.UUID;

public class RatingController {

    public static int getPlayerCombatRating(UUID uuid) {
        if (CombatRankingSystem.getPlayerStatsCache().containsKey(uuid)) {
            return CombatRankingSystem.getPlayerStatsCache().get(uuid).getCombatRating();
        }
        return CombatRankingSystem.getPlugin().getConfig().getInt("initial-combat-rating");
    }


//    public static int getPlayerCombatRating(Player player) {
//        PersistentDataContainer playerData = player.getPersistentDataContainer();
//        if (playerData.has(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER)) {
//            return playerData.get(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER);
//        } else {
//            return 0;
//        }
//    }
//
//    public static void setPlayerCombatRating(Player player, int newRating) {
//        PersistentDataContainer playerData = player.getPersistentDataContainer();
//        if (newRating >= 0 && playerData.has(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER)) {
//            playerData.set(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER, newRating);
//        }
//    }
//
//    public static void initPlayerCombatRating(Player player, int newRating) {
//        PersistentDataContainer playerData = player.getPersistentDataContainer();
//        if (newRating >= 0) {
//            playerData.set(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER, newRating);
//        }
//    }
//
//    public static boolean hasPlayerCombatRating(Player player) {
//        return player.getPersistentDataContainer().has(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER);
//    }
}
