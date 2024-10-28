package me.Cashtann.combatRankingSystem.commands;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.ranking.RatingController;
import me.Cashtann.combatRankingSystem.utilities.StringFormatter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CRSCommand implements CommandExecutor {

    private static void sendCommandOutput(boolean commandSuccess, Player player, String message) {

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

    private static void sendInvalidCommandOutput(Player player) {
        String message = "Invalid command!";
        sendCommandOutput(false, player, message);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player player) {
            String success_prefix = CombatRankingSystem.getCommandSuccessOutputPrefix();
            String failed_prefix = CombatRankingSystem.getCommandFailedOutputPrefix();

            if (strings.length == 0) { return true; }

            switch (strings[0]) {
                case "reload":
                    if (strings.length == 1) {
                        sendCommandOutput(true, player, "Reloading config...");
                        long timeElapsed = System.currentTimeMillis();

                        CombatRankingSystem.getPlugin().reloadConfig();

                        timeElapsed = System.currentTimeMillis() - timeElapsed;
                        String message = "Configuration reloaded successfully in " + String.valueOf(timeElapsed) + " ms!";
                        
                        sendCommandOutput(true, player, message);
                        return true;
                    } else {
                        sendInvalidCommandOutput(player);
                        return true;
                    }
                case "get":
                    if (strings.length == 2) {
                        OfflinePlayer wanted = Bukkit.getOfflinePlayer(strings[1]);
                        if (wanted.hasPlayedBefore()) {
                            String message = wanted.getName();
                            message += "'s rating is: ";
                            //message = message + RatingController.getPlayerCombatRating(wanted);
                            sendCommandOutput(true, player, message);
                            return true;
                        } else {
                            String message = "Player ";
                            message += wanted.getName();
                            message += " has never been on the server!";
                            sendCommandOutput(false, player, message);
                            return true;
                        }
                    }
                default:
                    sendInvalidCommandOutput(player);
                    return true;
            }
        }


        return true;
    }
}
