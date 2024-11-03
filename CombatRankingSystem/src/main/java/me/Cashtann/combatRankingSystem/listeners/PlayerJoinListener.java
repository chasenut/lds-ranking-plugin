package me.Cashtann.combatRankingSystem.listeners;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.files.PlayersStatsContainer;
import me.Cashtann.combatRankingSystem.ranking.RatingController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

//        if (!RatingController.hasPlayerCombatRating(player)) {
//            int initialCombatRating = CombatRankingSystem.getPlugin().getConfig().getInt("initial-combat-rating");
//            RatingController.initPlayerCombatRating(player, initialCombatRating);
//        }

        PlayersStatsContainer.loadOrCreatePlayerStats(player.getUniqueId());
    }
}
