package me.Cashtann.combatRankingSystem.commands;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.files.PlayersStatsContainer;
import me.Cashtann.combatRankingSystem.utilities.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class CRSGetCommand extends CRSSubCommand {
    @Override
    public String getName() {
        return "get";
    }

    @Override
    public String getDescription() {
        return "Get the player's statistic";
    }

    @Override
    public String getSyntax() {
        return "/crs get <player> <type>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length > 1) {
            if (Bukkit.getPlayerExact(args[1]) != null) {
                // Getting an instance of the plugin
                CombatRankingSystem plugin = CombatRankingSystem.getPlugin();

                String targetName = args[1];
                // Get OfflinePlayer
                OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(targetName);
                UUID targetUUID = offlineTarget.getUniqueId();

                // Check if target is online
                Player onlineTarget = Bukkit.getPlayerExact(targetName);

                String scoreType = args[2];

                if (Objects.equals(scoreType, "combatRating")) {
                    if (onlineTarget != null) { // Target is online
                        int value = plugin.getPlayerStatsCache().get(onlineTarget.getUniqueId()).getCombatRating();
                        String message = targetName;
                        message += "'s combat rating: ";
                        message += String.valueOf(value);
                        PlayerMessage.sendCommandOutput(true, player, message);
                    } else { // Target if offline
                        if (offlineTarget.hasPlayedBefore()) {
                            // Loads the player stats from the config if they've played before
                            PlayersStatsContainer.loadOrCreatePlayerStats(targetUUID);
                            int value = plugin.getPlayerStatsCache().get(offlineTarget.getUniqueId()).getCombatRating();
                            String message = targetName;
                            message += "'s combat rating: ";
                            message += String.valueOf(value);
                            PlayerMessage.sendCommandOutput(true, player, message);
                        } else {
                            errorPlayerHasNotPlayedBefore(player);
                        }
                    }
                } else if (Objects.equals(scoreType, "kills")) {
                    if (onlineTarget != null) { // Target is online
                        int value = plugin.getPlayerStatsCache().get(onlineTarget.getUniqueId()).getKills();
                        String message = targetName;
                        message += "'s kills: ";
                        message += String.valueOf(value);
                        PlayerMessage.sendCommandOutput(true, player, message);
                    } else { // Target if offline
                        if (offlineTarget.hasPlayedBefore()) {
                            // Loads the player stats from the config if they've played before
                            PlayersStatsContainer.loadOrCreatePlayerStats(targetUUID);
                            int value = plugin.getPlayerStatsCache().get(offlineTarget.getUniqueId()).getKills();
                            String message = targetName;
                            message += "'s kills: ";
                            message += String.valueOf(value);
                            PlayerMessage.sendCommandOutput(true, player, message);
                        } else {
                            errorPlayerHasNotPlayedBefore(player);
                        }
                    }
                } else if (Objects.equals(scoreType, "deaths")) {
                    if (onlineTarget != null) { // Target is online
                        int value = plugin.getPlayerStatsCache().get(onlineTarget.getUniqueId()).getDeaths();
                        String message = targetName;
                        message += "'s deaths: ";
                        message += String.valueOf(value);
                        PlayerMessage.sendCommandOutput(true, player, message);
                    } else { // Target if offline
                        if (offlineTarget.hasPlayedBefore()) {
                            // Loads the player stats from the config if they've played before
                            PlayersStatsContainer.loadOrCreatePlayerStats(targetUUID);
                            int value = plugin.getPlayerStatsCache().get(offlineTarget.getUniqueId()).getDeaths();
                            String message = targetName;
                            message += "'s deaths: ";
                            message += String.valueOf(value);
                            PlayerMessage.sendCommandOutput(true, player, message);
                        } else {
                            errorPlayerHasNotPlayedBefore(player);
                        }
                    }
                } else if (Objects.equals(scoreType, "minedStone")) {
                    if (onlineTarget != null) { // Target is online
                        int value = plugin.getPlayerStatsCache().get(onlineTarget.getUniqueId()).getMinedStone();
                        String message = targetName;
                        message += "'s mined stone: ";
                        message += String.valueOf(value);
                        PlayerMessage.sendCommandOutput(true, player, message);
                    } else { // Target if offline
                        if (offlineTarget.hasPlayedBefore()) {
                            // Loads the player stats from the config if they've played before
                            PlayersStatsContainer.loadOrCreatePlayerStats(targetUUID);
                            int value = plugin.getPlayerStatsCache().get(offlineTarget.getUniqueId()).getMinedStone();
                            String message = targetName;
                            message += "'s mined stone: ";
                            message += String.valueOf(value);
                            PlayerMessage.sendCommandOutput(true, player, message);
                        } else {
                            errorPlayerHasNotPlayedBefore(player);
                        }
                    }
                } else if (Objects.equals(scoreType, "distance")) {
                    if (onlineTarget != null) { // Target is online
                        int value = plugin.getPlayerStatsCache().get(onlineTarget.getUniqueId()).getDistance_cm() / 100;
                        String message = targetName;
                        message += "'s distance (m): ";
                        message += String.valueOf(value);
                        PlayerMessage.sendCommandOutput(true, player, message);
                    } else { // Target if offline
                        if (offlineTarget.hasPlayedBefore()) {
                            // Loads the player stats from the config if they've played before
                            PlayersStatsContainer.loadOrCreatePlayerStats(targetUUID);
                            int value = plugin.getPlayerStatsCache().get(offlineTarget.getUniqueId()).getDistance_cm() / 100;
                            String message = targetName;
                            message += "'s distance (m): ";
                            message += String.valueOf(value);
                            PlayerMessage.sendCommandOutput(true, player, message);
                        } else {
                            errorPlayerHasNotPlayedBefore(player);
                        }
                    }
                } else if (Objects.equals(scoreType, "playtime")) {
                    if (onlineTarget != null) { // Target is online
                        int value = plugin.getPlayerStatsCache().get(onlineTarget.getUniqueId()).getPlaytime_t() / 20 / 60;
                        String message = targetName;
                        message += "'s playtime (min): ";
                        message += String.valueOf(value);
                        PlayerMessage.sendCommandOutput(true, player, message);
                    } else { // Target if offline
                        if (offlineTarget.hasPlayedBefore()) {
                            // Loads the player stats from the config if they've played before
                            PlayersStatsContainer.loadOrCreatePlayerStats(targetUUID);
                            int value = plugin.getPlayerStatsCache().get(offlineTarget.getUniqueId()).getPlaytime_t() / 20 / 60;
                            String message = targetName;
                            message += "'s playtime (min): ";
                            message += String.valueOf(value);
                            PlayerMessage.sendCommandOutput(true, player, message);
                        } else {
                            errorPlayerHasNotPlayedBefore(player);
                        }
                    }
                }

            } else {
                PlayerMessage.sendCommandOutput(false, player, "Specified player either doesn't or has not joined the server before");
            }
        } else if (args.length == 1) {
            PlayerMessage.sendCommandOutput(false, player, "You did not provide the player name, please try again");
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {

        if (args.length == 2) {
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (Player value : players) {
                playerNames.add(value.getName());
            }
            return playerNames;
        } else if (args.length == 3) {
            List<String> stringValues = Arrays.asList("combatRating", "kills", "deaths", "minedStone", "distance", "playtime");
            return  stringValues;
        }

        return List.of();
    }

    private void errorPlayerHasNotPlayedBefore(Player player) {
        PlayerMessage.sendCommandOutput(false, player, "Specified player has not played on the server before");
    }
}
