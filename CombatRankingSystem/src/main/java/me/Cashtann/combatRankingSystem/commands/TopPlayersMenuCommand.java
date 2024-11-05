package me.Cashtann.combatRankingSystem.commands;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.files.PlayersStatsContainer;
import me.Cashtann.combatRankingSystem.leaderboard.LeaderboardManager;
import me.Cashtann.combatRankingSystem.utilities.StringFormatter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TopPlayersMenuCommand implements TabExecutor {

    private CombatRankingSystem plugin;
    private LeaderboardManager leaderboard;

    public TopPlayersMenuCommand(CombatRankingSystem plugin) {
        this.plugin = plugin;
        leaderboard = plugin.getLeaderboardManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {

            FileConfiguration config = plugin.getConfig();

            String main_style = config.getString("topplayers.main-style");

            String inventoryName = config.getString("topplayers.inventory-name");


            int inventorySize = 27;
            Inventory inventory = Bukkit.createInventory(player, inventorySize, StringFormatter.formatString(inventoryName));

            ItemStack combatRatingItem = new ItemStack(Material.NETHER_STAR, 1);
            ItemMeta combatRatingItemMeta = combatRatingItem.getItemMeta();
            List<Map.Entry<String, Integer>> topCombatRatingPlayers = leaderboard.getTopPlayers("combatRating");
            combatRatingItemMeta.setDisplayName(StringFormatter.formatString(main_style + "Ranking"));
            combatRatingItemMeta.setLore(getLeaderboardLore(player, topCombatRatingPlayers, "combatRating"));
            combatRatingItem.setItemMeta(combatRatingItemMeta);
            inventory.setItem(10, combatRatingItem);

            ItemStack emptySpaceItemStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
            ItemMeta emptySpaceItemMeta = emptySpaceItemStack.getItemMeta();
            emptySpaceItemMeta.setDisplayName(" ");
            emptySpaceItemMeta.setLore(new ArrayList<>());
            emptySpaceItemStack.setItemMeta(emptySpaceItemMeta);
            for (int i = 0; i < inventorySize; i++) {
                ItemStack item = inventory.getItem(i);
                if (item == null || item.getType() == Material.AIR) {
                    inventory.setItem(i, emptySpaceItemStack);
                }
            }

            player.openInventory(inventory);
        }
        return true;
    }

    private List<String> getLeaderboardLore(Player player, List<Map.Entry<String, Integer>> leaderboard, String category) {
        FileConfiguration config = plugin.getConfig();
        //String main_style = config.getString("topplayers.main-style");
        String playerScore = switch (category) {
            case "combatRating" ->
                    String.valueOf(plugin.getPlayerStatsCache().get(player.getUniqueId()).getCombatRating());
            case "kills" -> String.valueOf(plugin.getPlayerStatsCache().get(player.getUniqueId()).getKills());
            case "deaths" -> String.valueOf(plugin.getPlayerStatsCache().get(player.getUniqueId()).getDeaths());
            case "minedStone" -> String.valueOf(plugin.getPlayerStatsCache().get(player.getUniqueId()).getMinedStone());
            case "distance" ->
                    String.valueOf(plugin.getPlayerStatsCache().get(player.getUniqueId()).getDistance_cm() / 100) + " m";
            case "playtime" ->
                    String.valueOf(plugin.getPlayerStatsCache().get(player.getUniqueId()).getPlaytime_t() / 20 / 60) + " min";
            default -> " ";
        };
        String counter_style = config.getString("topplayers.counter-style");
        String text_style = config.getString("topplayers.text-style");
        String score_style = config.getString("topplayers.score-style");

        List<String> finalLore = new ArrayList<>();
        finalLore.add(" ");
        finalLore.add(StringFormatter.formatString(text_style + "Twoja wartość: " + score_style + playerScore));
        finalLore.add(" ");

        for (int i = 0; i < leaderboard.size(); i++) {
            Map.Entry<String, Integer> entry = leaderboard.get(i);
            String playerName = entry.getKey(); // Get the player's name
            String score = String.valueOf(entry.getValue()); // Get the combat rating
            String lore = StringFormatter.formatString(counter_style + String.valueOf(i+1) + " " + text_style + playerName + " " + score_style + score);
            finalLore.add(lore);
        }

        finalLore.add(" ");
        String time_between_saves = String.valueOf(plugin.getConfig().getInt("data-save"));
        finalLore.add(StringFormatter.formatString("&7Ranking jest odświeżany co " + time_between_saves + " sekund"));

        return finalLore;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return List.of();
    }
}
