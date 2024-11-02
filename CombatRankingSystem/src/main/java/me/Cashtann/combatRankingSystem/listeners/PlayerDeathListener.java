package me.Cashtann.combatRankingSystem.listeners;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.ranking.RatingController;
import me.Cashtann.combatRankingSystem.utilities.StringFormatter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final CombatRankingSystem plugin;

    public PlayerDeathListener(CombatRankingSystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();

        if (killer != null && player != null) {
            double multiplier = plugin.getConfig().getDouble("combat-score-modifier");
            int difference = (int) (RatingController.getPlayerCombatRating(player) * multiplier);

            int newKillerRating = RatingController.getPlayerCombatRating(killer) + difference;
            int newPlayerRating = RatingController.getPlayerCombatRating(player) - difference;

            RatingController.setPlayerCombatRating(killer, newKillerRating);
            RatingController.setPlayerCombatRating(player, newPlayerRating);

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

