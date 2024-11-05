package me.Cashtann.combatRankingSystem.tasks;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.files.PlayersStatsContainer;
import me.Cashtann.combatRankingSystem.ranking.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class SavePlayerStatsTask extends BukkitRunnable {

    CombatRankingSystem plugin;

    public SavePlayerStatsTask(CombatRankingSystem plugin) {
        this.plugin = plugin;
    }


    @Override
    public void run() {
        PlayersStatsContainer.saveConfig();
        Bukkit.getServer().getLogger().info("Saved players stats");
        saveCacheToConfig();
        plugin.getLeaderboardManager().updateLeaderboards();
    }

    private void saveCacheToConfig() {
        plugin.saveCacheToConfig();
    }
}
