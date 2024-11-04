package me.Cashtann.combatRankingSystem.ranking;

import org.bukkit.Bukkit;

public class PlayerStats {
    private int combatRating;
    private int kills;
    private int deaths;
    private int minedStone;
    private int distance_cm;
    private int playtime_t;

    public void setCombatRating(int combatRating) {
        this.combatRating = Math.max(combatRating, 0);
    }

    public void setKills(int kills) {
        this.kills = Math.max(kills, 0);
    }

    public void setDeaths(int deaths) {
        this.deaths = Math.max(deaths, 0);
    }

    public void setMinedStone(int minedStone) {
        this.minedStone = Math.max(minedStone, 0);
    }

    public void setDistance_cm(int distance_cm) {
        this.distance_cm = Math.max(distance_cm, 0);
    }

    public void setPlaytime_t(int playtime_t) {
        this.playtime_t = Math.max(playtime_t, 0);
    }

    public int getCombatRating() {
        return combatRating;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getMinedStone() {
        return minedStone;
    }

    public int getDistance_cm() {
        return distance_cm;
    }

    public int getPlaytime_t() {
        return playtime_t;
    }

    public PlayerStats(int combatRating, int kills, int deaths, int minedStone, int distance_cm, int playtime_t) {
//        this.combatRating = combatRating;
//        this.kills = kills;
//        this.deaths = deaths;
//        this.minedStone = minedStone;
//        this.distance_cm = distance_cm;
//        this.playtime_t = playtime_t;
        setCombatRating(combatRating);
        setKills(kills);
        setDeaths(deaths);
        setMinedStone(minedStone);
        setDistance_cm(distance_cm);
        setPlaytime_t(playtime_t);
    }
}
