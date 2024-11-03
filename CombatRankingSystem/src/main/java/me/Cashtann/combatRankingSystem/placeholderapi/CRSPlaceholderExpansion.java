package me.Cashtann.combatRankingSystem.placeholderapi;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.ranking.RatingController;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

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
            int rating = CombatRankingSystem.getPlayerStatsCache().get(player.getUniqueId()).getCombatRating();
            return String.valueOf(rating);
        } else if (identifier.equals("player_kills")) {
            int kills = player.getStatistic(Statistic.PLAYER_KILLS);
            return String.valueOf(kills);
        } else if (identifier.equals("player_deaths")) {
            int deaths = player.getStatistic(Statistic.DEATHS);
            return String.valueOf(deaths);
        } else if (identifier.equals("player_mined_stone")) {
            int mined = player.getStatistic(Statistic.MINE_BLOCK, Material.STONE);
            return String.valueOf(mined);
        } else if (identifier.equals("player_distance")) { //traveled distance
            int distance = player.getStatistic(Statistic.WALK_ONE_CM);
            return String.valueOf(distance);
        } else if (identifier.equals("player_playtime_hours")) {
            double playtime = (double) player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60 / 60; //in hours
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(playtime);
        } else if (identifier.equals("player_playtime_minutes")) {
            double playtime = (double) player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60; //in minutes
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(playtime);
        } else if (identifier.equals("player_balance")) {
            //int balance = < logic here >
            return String.valueOf(0);
        }

        return "error";
    }
}
