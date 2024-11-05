package me.Cashtann.combatRankingSystem;

import me.Cashtann.combatRankingSystem.commands.CRSCommandManager;
import me.Cashtann.combatRankingSystem.commands.TopPlayersMenuCommand;
import me.Cashtann.combatRankingSystem.files.PlayersStatsContainer;
import me.Cashtann.combatRankingSystem.leaderboard.LeaderboardManager;
import me.Cashtann.combatRankingSystem.listeners.ChatMessageListener;
import me.Cashtann.combatRankingSystem.listeners.InventoryClickListener;
import me.Cashtann.combatRankingSystem.listeners.PlayerDeathListener;
import me.Cashtann.combatRankingSystem.listeners.PlayerJoinListener;
import me.Cashtann.combatRankingSystem.placeholderapi.CRSPlaceholderExpansion;
import me.Cashtann.combatRankingSystem.ranking.PlayerStats;
import me.Cashtann.combatRankingSystem.tasks.SavePlayerStatsTask;
import me.Cashtann.combatRankingSystem.utilities.StringFormatter;
import me.Cashtann.combatRankingSystem.utilities.VariousPlayerStatsUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class CombatRankingSystem extends JavaPlugin implements Listener {

    private static CombatRankingSystem plugin;

    private static String commandSuccessOutputPrefix;
    private static String commandFailedOutputPrefix;

    private HashMap<UUID, PlayerStats> playerStatsCache = new HashMap<>();
    private LeaderboardManager leaderboardManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Config load and save
        this.saveDefaultConfig();

        plugin = this;

        // Player stats config (data storage)
        PlayersStatsContainer.setup();
        PlayersStatsContainer.getConfig().options().copyDefaults(true);
        PlayersStatsContainer.saveConfig();
        // Saving the config on runtime
        loadOnlinePlayersStats();
        long time_between_saves = plugin.getConfig().getLong("data-save");
        BukkitTask savePlayerStats = new SavePlayerStatsTask(this).runTaskTimer(this, 0L, time_between_saves);

        // Leaderboards
        leaderboardManager = new LeaderboardManager();

        // Listeners register
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new ChatMessageListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);

        // PlaceholderAPI register
        new CRSPlaceholderExpansion(this).register();

        // Commands
        getCommand("crs").setExecutor(new CRSCommandManager());
        getCommand("topplayers").setExecutor(new TopPlayersMenuCommand(this));

        getLogger().info("============================= ");
        getLogger().info("Combat Ranking System Loaded! ");
        getLogger().info("============================= ");

        getServer().broadcastMessage(StringFormatter.formatString(" \n\n       &dCombat Ranking System powered by Cashtann\n\n"));

        commandSuccessOutputPrefix = getPlugin().getConfig().getString("commands.prefix-success");
        commandFailedOutputPrefix = getPlugin().getConfig().getString("commands.prefix-failed");
    }

    @Override
    public void onDisable() {
        saveCacheToConfig();
        PlayersStatsContainer.saveConfig();
    }


    public static CombatRankingSystem getPlugin() {
        return plugin;
    }

    public static String getCommandFailedOutputPrefix() {
        return commandFailedOutputPrefix;
    }

    public static String getCommandSuccessOutputPrefix() {
        return commandSuccessOutputPrefix;
    }

    public HashMap<UUID, PlayerStats> getPlayerStatsCache() {
        return this.playerStatsCache;
    }

    public void setPlayerStatsCache(HashMap<UUID, PlayerStats> playerStatsCache) {
        this.playerStatsCache = playerStatsCache;
    }

    private void loadOnlinePlayersStats() {
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (Player player : players) {
            PlayersStatsContainer.loadOrCreatePlayerStats(player.getUniqueId());
        }
    }

    public void saveCacheToConfig() {
        List<UUID> playersToRemoveFromCache = new ArrayList<>();
        for (UUID uuid : this.playerStatsCache.keySet()) {
            // Get player stats (cache)
            PlayerStats stats = this.playerStatsCache.get(uuid);
            // Get player
            Player player = Bukkit.getPlayer(uuid);
            // Check if player is online, otherwise add him to the list of
            // UUIDs to be removed from cache to keep memory clean
            if (player == null) {
                // Player is offline
                playersToRemoveFromCache.add(uuid);
            } else {
                // Player is online
                // Update cache's player stats that have not been updated
                // like minedStone, distance, playtime etc. (combatRating, kills and deaths are updated
                // on EventListeners, these are updated from player stats every save (/crs set command
                // should set player's statistics in meanwhile
                stats.setMinedStone(player.getStatistic(Statistic.MINE_BLOCK, Material.STONE));
                int distance = VariousPlayerStatsUtils.getPlayerWalkedDistanceByFoot(player);
                stats.setDistance_cm(distance);
                stats.setPlaytime_t(player.getStatistic(Statistic.PLAY_ONE_MINUTE));
            }

            // Save cache to config
            PlayersStatsContainer.addPlayerData(uuid,
                    stats.getCombatRating(),
                    stats.getKills(),
                    stats.getDeaths(),
                    stats.getMinedStone(),
                    stats.getDistance_cm(),
                    stats.getPlaytime_t());
        }
        PlayersStatsContainer.saveConfig();
        // Cleaning memory
        for (UUID uuid : playersToRemoveFromCache) {
            playerStatsCache.remove(uuid);
        }
    }


    public LeaderboardManager getLeaderboardManager() {
        return leaderboardManager;
    }
}
