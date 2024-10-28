package me.Cashtann.combatRankingSystem.placeholderapi;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.ranking.RatingController;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CRSPlaceholderExpansion extends PlaceholderExpansion {

    private final CombatRankingSystem plugin;

    public CRSPlaceholderExpansion(CombatRankingSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "crs";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "";
        }

        if (identifier.equals("player_rating")) {
            int rating = RatingController.getPlayerCombatRating(player);
            return String.valueOf(rating);
        }

        return "error";
    }
}
