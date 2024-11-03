package me.Cashtann.combatRankingSystem.commands;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.files.PlayersStatsContainer;
import me.Cashtann.combatRankingSystem.ranking.RatingController;
import me.Cashtann.combatRankingSystem.utilities.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

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
        return "/crs set <player> <type> <amount>";
    }

    private String scoreType;

    @Override
    public void perform(Player player, String[] args) {
        if (args.length > 2) {
            //if (Bukkit.getPlayerExact(args[1]) != null) {

            String targetName = args[1];
            // Get OfflinePlayer
            OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(targetName);
            UUID targetUUID = offlineTarget.getUniqueId();

            // Check if target is online
            Player onlineTarget = Bukkit.getPlayerExact(targetName);
            //UUID targetUUID = UUID.fromString(args[1]);

            scoreType = args[2];

            int newValue;
            try {
                newValue = Integer.parseInt(args[3]);
                if (newValue < 0) {
                    PlayerMessage.sendCommandOutput(false, player, "Specified amount should be greater or equal to 0");
                    return;
                }
            } catch (NumberFormatException e) {
                PlayerMessage.sendCommandOutput(false, player, "Specified amount is not a valid number (integer greater or equal to 0)");
                return;
            }

            if (Objects.equals(scoreType, "combatRating")) {
                if (onlineTarget != null) { // Target is online
                    CombatRankingSystem.getPlayerStatsCache().get(onlineTarget.getUniqueId()).setCombatRating(newValue);
                    String message = targetName;
                    message += "'s rating set to ";
                    message += String.valueOf(newValue);
                    PlayerMessage.sendCommandOutput(true, player, message);
                } else { // Target if offline
                    if (offlineTarget.hasPlayedBefore()) {
                        // Loads the player stats from the config if they've played before
                        PlayersStatsContainer.loadOrCreatePlayerStats(targetUUID);
                        CombatRankingSystem.getPlayerStatsCache().get(offlineTarget.getUniqueId()).setCombatRating(newValue);
                        String message = targetName;
                        message += "'s rating set to ";
                        message += String.valueOf(newValue);
                        PlayerMessage.sendCommandOutput(true, player, message);
                    } else {
                        errorPlayerHasNotPlayedBefore(player);
                    }
                }
            } else if (Objects.equals(scoreType, "kills")) {

            } else if (Objects.equals(scoreType, "deaths")) {

            } else if (Objects.equals(scoreType, "minedStone")) {

            } else if (Objects.equals(scoreType, "distance")) {

            } else if (Objects.equals(scoreType, "playtime")) {

            }
            //RatingController.setPlayerCombatRating(target, newRating);
            //CombatRankingSystem.getPlayerStatsCache().get(target.getUniqueId()).setCombatRating(newRating);
            //CombatRankingSystem.getPlayerStatsCache().get(targetUUID).setCombatRating(newValue);
//            } else {
//                PlayerMessage.sendCommandOutput(false, player, "Specified player either doesn't exist or is offline");
//            }
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
            List<String> stringValues = Arrays.asList("combatRating", "kills", "deaths", "minedStone", "distance", "playtime");

            return stringValues;
        } else if (args.length == 4) {
            List<String> stringValues = Arrays.asList("1000", "500", "100");

            return stringValues;
        }

        return List.of();
    }

    private void errorPlayerHasNotPlayedBefore(Player player) {
        PlayerMessage.sendCommandOutput(false, player, "Specified player has not played on the server before");
    }
}
