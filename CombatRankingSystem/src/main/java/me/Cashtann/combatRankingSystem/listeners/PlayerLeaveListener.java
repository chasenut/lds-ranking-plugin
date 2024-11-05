package me.Cashtann.combatRankingSystem.listeners;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.files.PlayersStatsContainer;
import me.Cashtann.combatRankingSystem.ranking.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        List<String> playerNames = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);

        UUID uuid = player.getUniqueId();
        PlayerStats stats = CombatRankingSystem.getPlugin().getPlayerStatsCache().get(uuid);
        // Save cache to config
        PlayersStatsContainer.addPlayerData(uuid,
                stats.getCombatRating(),
                stats.getKills(),
                stats.getDeaths(),
                stats.getMinedStone(),
                stats.getDistance_cm(),
                stats.getPlaytime_t());

    }
}
