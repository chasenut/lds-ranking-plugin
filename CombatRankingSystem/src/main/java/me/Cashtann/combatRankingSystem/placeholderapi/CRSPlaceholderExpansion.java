package me.Cashtann.combatRankingSystem.placeholderapi;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
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
            int rating = plugin.getPlayerStatsCache().get(player.getUniqueId()).getCombatRating();
            return String.valueOf(rating);
        } else if (identifier.equals("player_kills")) {
            int kills = plugin.getPlayerStatsCache().get(player.getUniqueId()).getKills();
            return String.valueOf(kills);
        } else if (identifier.equals("player_deaths")) {
            int deaths = plugin.getPlayerStatsCache().get(player.getUniqueId()).getDeaths();
            return String.valueOf(deaths);
        } else if (identifier.equals("player_mined_stone")) {
            int mined = plugin.getPlayerStatsCache().get(player.getUniqueId()).getMinedStone();
            return String.valueOf(mined);
        } else if (identifier.equals("player_distance")) { //traveled distance (cm)
            int distance = plugin.getPlayerStatsCache().get(player.getUniqueId()).getDistance_cm();
            return String.valueOf(distance);
        } else if (identifier.equals("player_distance_meters")) { //traveled distance (meters)
            int distance = plugin.getPlayerStatsCache().get(player.getUniqueId()).getDistance_cm() / 100;
            return String.valueOf(distance);
        } else if (identifier.equals("player_playtime_hours")) {
            double playtime = (double) plugin.getPlayerStatsCache().get(player.getUniqueId()).getPlaytime_t() / 20 / 60 / 60; //in hours
            DecimalFormat df = new DecimalFormat("#.00");
            return df.format(playtime);
        } else if (identifier.equals("player_playtime_minutes")) {
            double playtime = (double) plugin.getPlayerStatsCache().get(player.getUniqueId()).getPlaytime_t() / 20 / 60; //in minutes
//            DecimalFormat df = new DecimalFormat("#.00");
//            return df.format(playtime);
            return String.valueOf(Math.round(playtime));
        } else if (identifier.equals("player_balance")) {
            //int balance = < logic here >
            return String.valueOf(0);
        }

        return "error";
    }
}
