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


    public static int setPlayerCombatRating(Player player, int newRating) {
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        if (newRating >= 0 && playerData.has(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER)) {
            playerData.set(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER, newRating);
            return getPlayerCombatRating(player); // New rating
        }
//        Return 0 when newRating below 0 or if player does not have rating somehow
        return 0;
    }

    public static int resetPlayerCombatRating(Player player) {
        int defaultCombatRating = CombatRankingSystem.getPlugin().getConfig().getInt("initial-combat-rating");
        setPlayerCombatRating(player, defaultCombatRating);
        return defaultCombatRating;
    }

    public static int getMultipliedPlayerCombatRating(Player player, double multiplier) {
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        if (multiplier >= 0 && playerData.has(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER)) {
            int newRating = (int) Math.round(getPlayerCombatRating(player) * multiplier);
            if (getPlayerCombatRating(player) < 0) {
                return 0;
            } else {
                return newRating; // New rating
            }
        }
//        Return 0 when multiplier below 0 or if player does not have rating somehow
        return 0;
    }


    public static int setMultipliedPlayerCombatRating(Player player, double multiplier) {
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        if (multiplier >= 0 && playerData.has(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER)) {
            int newRating = (int) Math.round(getPlayerCombatRating(player) * multiplier);
            playerData.set(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER, newRating);
            if (getPlayerCombatRating(player) < 0) {
                return 0;
            } else {
                return getPlayerCombatRating(player); // New rating
            }
        }
//        Return 0 when multiplier below 0 or if player does not have rating somehow
        return 0;
    }

    public static int addPlayerCombatRating(Player player, int num) {
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        if (num >= 0 && playerData.has(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER)) {
            int newRating = getPlayerCombatRating(player) + num;
            setPlayerCombatRating(player, newRating);
            return newRating;
        }
        return 0;
    }

    public static int subtractPlayerCombatRating(Player player, int num) {
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        if (num >= 0 && playerData.has(new NamespacedKey(CombatRankingSystem.getPlugin(), "combat_rating"), PersistentDataType.INTEGER)) {
            int newRating = getPlayerCombatRating(player) - num;
            if (newRating >= 0) {
                setPlayerCombatRating(player, newRating);
            } else {
                setPlayerCombatRating(player, 0);
            }
            return newRating;
        }
        return 0;
    }
}
