package me.Cashtann.combatRankingSystem.listeners;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.ranking.RatingController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatMessageListener implements Listener {

    private final CombatRankingSystem plugin;

    public ChatMessageListener(CombatRankingSystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChatMessageSend(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();

        String format = String.valueOf(RatingController.getPlayerCombatRating(sender));

        format = format + event.getFormat();
        event.setFormat(format);

    }
}
