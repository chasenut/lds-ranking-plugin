package me.Cashtann.combatRankingSystem.commands;

import me.Cashtann.combatRankingSystem.ranking.RatingController;
import me.Cashtann.combatRankingSystem.utilities.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CRSSetCommand extends CRSSubCommand {
    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getDescription() {
        return "Sets rating for specified player";
    }

    @Override
    public String getSyntax() {
        return "/crs set <player> <amount>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length > 2) {
            if (Bukkit.getPlayerExact(args[1]) != null) {
                Player target = Bukkit.getPlayerExact(args[1]);

                int newRating;
                try {
                    newRating = Integer.parseInt(args[2]);
                    if (newRating < 0) {
                        PlayerMessage.sendCommandOutput(false, player, "Specified amount should be greater or equal to 0");
                        return;
                    }
                } catch (NumberFormatException e) {
                    PlayerMessage.sendCommandOutput(false, player, "Specified amount is not a valid number (integer greater or equal to 0)");
                    return;
                }

                RatingController.setPlayerCombatRating(target, newRating);

                String message = target.getName();
                message += "'s rating set to ";
                message += String.valueOf(newRating);
                PlayerMessage.sendCommandOutput(true, player, message);
            } else {
                PlayerMessage.sendCommandOutput(false, player, "Specified player either doesn't exist or is offline");
            }
        } else {
            PlayerMessage.sendCommandOutput(false, player, "Invalid arguments");
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {

        if (args.length == 2) {
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                playerNames.add(players[i].getName());
            }
            return playerNames;
        } else if (args.length == 3) {
            List<String> stringValues = Arrays.asList("1000", "500", "100");

            return stringValues;
        }

        return List.of();
    }
}
