package me.Cashtann.combatRankingSystem.listeners;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.files.PlayersStatsContainer;
import me.Cashtann.combatRankingSystem.ranking.PlayerStats;
import me.Cashtann.combatRankingSystem.ranking.RatingController;
import me.Cashtann.combatRankingSystem.utilities.StringFormatter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDeathListener implements Listener {

    private final CombatRankingSystem plugin;
    private HashMap<UUID, PlayerStats> playerStatsCache;

    public PlayerDeathListener(CombatRankingSystem plugin, HashMap<UUID, PlayerStats> playerStatsCache) {
        this.plugin = plugin;
        this.playerStatsCache = playerStatsCache;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();

        if (killer != null && player != null) {
            // Update kills for killer
            UUID killerUUID = killer.getUniqueId();
            if (!playerStatsCache.containsKey(killerUUID)) {
                PlayersStatsContainer.getPlayerStats(killerUUID); // loads if are empty
            }
            PlayerStats killerStats = playerStatsCache.get(killerUUID);
            killerStats.setKills(killerStats.getKills() + 1);

            // Update deaths for player
            UUID playerUUID = killer.getUniqueId();
            if (!playerStatsCache.containsKey(playerUUID)) {
                PlayersStatsContainer.getPlayerStats(playerUUID); // loads if are empty
            }
            PlayerStats playerStats = playerStatsCache.get(playerUUID);
            playerStats.setDeaths(playerStats.getDeaths() + 1);

            // Calculate new combat ratings
            double multiplier = plugin.getConfig().getDouble("combat-score-modifier");
            int difference = (int) (playerStatsCache.get(playerUUID).getCombatRating() * multiplier);

            // Update combat rating
            killerStats.setCombatRating(killerStats.getCombatRating() + difference);
            playerStats.setCombatRating(playerStats.getCombatRating() - difference);



            String message = this.plugin.getConfig().getString("kill-message");
            message = message.replace("{player}", player.getName());
            message = message.replace("{killer}", killer.getName());
            message = message.replace("{player_diff}", String.valueOf(difference));
            message = message.replace("{killer_diff}", String.valueOf(difference));

            plugin.getServer().broadcastMessage(StringFormatter.formatString(message));
        }
//        else {
//            plugin.getServer().getLogger().info("[CombatRankingSystem] Ktoś zginąl nie z rąk innego gracza!");
//        }

    }
}

