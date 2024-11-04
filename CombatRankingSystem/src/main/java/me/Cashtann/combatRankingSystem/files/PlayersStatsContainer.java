package me.Cashtann.combatRankingSystem.files;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.ranking.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class PlayersStatsContainer {

    private static File statsFile;
    private static FileConfiguration statsConfig;
    private static HashMap<UUID, PlayerStats> playerStatsCache = CombatRankingSystem.getPlugin().getPlayerStatsCache();

    public static void setup() {
        statsFile = new File(Bukkit.getServer().getPluginManager().getPlugin("CombatRankingSystem").getDataFolder(), "data.yml");
        if (!statsFile.exists()) {
            try {
                statsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        statsConfig = YamlConfiguration.loadConfiguration(statsFile);
        if (!statsConfig.contains("players")) {
            statsConfig.createSection("players");
        }
    }

    public static void addPlayerData(UUID playerUUID, int combatRating, int kills, int deaths, int minedStone, int distance_cm, int playtime_t) {
        String path = "players." + playerUUID.toString();
        statsConfig.set(path + ".combatRating", combatRating);
        statsConfig.set(path + ".kills", kills);
        statsConfig.set(path + ".deaths", deaths);
        statsConfig.set(path + ".minedStone", minedStone);
        statsConfig.set(path + ".distance_cm", distance_cm);
        statsConfig.set(path + ".playtime_t", playtime_t);
        // Economy is the last, but it will be done with papi
    }

    public static PlayerStats getPlayerStats(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        String path = "players." + uuid.toString();
        int combatRating = statsConfig.getInt(path + ".combatRating", CombatRankingSystem.getPlugin().getConfig().getInt("initial-combat-rating"));
        int kills = statsConfig.getInt(path + ".kills");
        int deaths = statsConfig.getInt(path + ".deaths");
        int minedStone = statsConfig.getInt(path + ".minedStone");
        //int walked_distance = player.getStatistic(Statistic.WALK_ONE_CM) + player.getStatistic(Statistic.CROUCH_ONE_CM) + player.getStatistic(Statistic.SPRINT_ONE_CM);
        int distance_cm = statsConfig.getInt(path + ".distance_cm");
        int playtime_t = statsConfig.getInt(path + ".playtime_t");

        return new PlayerStats(combatRating, kills, deaths, minedStone, distance_cm, playtime_t);
    }

    public static void loadOrCreatePlayerStats(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (statsConfig.contains("players." + uuid.toString())) {
            playerStatsCache.put(uuid, getPlayerStats(uuid));
        } else {
            int walked_distance = player.getStatistic(Statistic.WALK_ONE_CM) + player.getStatistic(Statistic.CROUCH_ONE_CM) + player.getStatistic(Statistic.SPRINT_ONE_CM);
            PlayerStats defaultStats = new PlayerStats(CombatRankingSystem.getPlugin().getConfig().getInt("initial-combat-rating"),
                    player.getStatistic(Statistic.PLAYER_KILLS),
                    player.getStatistic(Statistic.DEATHS),
                    player.getStatistic(Statistic.MINE_BLOCK, Material.STONE),
                    walked_distance,
                    player.getStatistic(Statistic.PLAY_ONE_MINUTE));
            playerStatsCache.put(uuid, defaultStats);
        }

    }

    public static boolean containsOrInCacheUUID(UUID uuid) {
        if (CombatRankingSystem.getPlugin().getPlayerStatsCache().containsKey(uuid)) {
            return true;
        } else return statsConfig.contains("players." + uuid.toString());
    }

    public static void saveConfig() {
        try {
            statsConfig.save(statsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getConfig() {
        return statsConfig;
    }
}
