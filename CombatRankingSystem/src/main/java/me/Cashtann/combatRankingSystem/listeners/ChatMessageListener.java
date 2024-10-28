package me.Cashtann.combatRankingSystem.listeners;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.ranking.RatingController;
import me.Cashtann.combatRankingSystem.utilities.StringFormatter;
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

        if (plugin.getConfig().getBoolean("chat.rating.enabled")) {
            String prefix = plugin.getConfig().getString("chat.rating.prefix");
            prefix = prefix.replace("{rating}", String.valueOf(RatingController.getPlayerCombatRating(sender)));
            prefix = StringFormatter.formatString(prefix);

            String format = prefix + event.getFormat();
            event.setFormat(format);

        }

    }
}
