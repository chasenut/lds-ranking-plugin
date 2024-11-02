package me.Cashtann.combatRankingSystem.utilities;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import org.bukkit.entity.Player;

public class PlayerMessage {

    public static void sendCommandOutput(boolean commandSuccess, Player player, String message) {

        String success_prefix = CombatRankingSystem.getCommandSuccessOutputPrefix();
        String failed_prefix = CombatRankingSystem.getCommandFailedOutputPrefix();

        String finalMessage;
        if (commandSuccess) {
            finalMessage = success_prefix;
        } else {
            finalMessage = failed_prefix;
        }
        finalMessage += message;

        player.sendMessage(StringFormatter.formatString(finalMessage));
    }

    public static void sendInvalidCommandOutput(Player player) {
        String message = "Invalid command!";
        sendCommandOutput(false, player, message);
    }

    public static void sendFormattedMessage(Player player, String message) {
        player.sendMessage(StringFormatter.formatString(message));
    }

}
