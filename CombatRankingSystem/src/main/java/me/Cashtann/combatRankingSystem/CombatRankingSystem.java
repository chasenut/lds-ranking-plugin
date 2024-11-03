package me.Cashtann.combatRankingSystem;

import me.Cashtann.combatRankingSystem.commands.CRSCommandManager;
import me.Cashtann.combatRankingSystem.files.PlayersStatsContainer;
import me.Cashtann.combatRankingSystem.listeners.ChatMessageListener;
import me.Cashtann.combatRankingSystem.listeners.PlayerDeathListener;
import me.Cashtann.combatRankingSystem.listeners.PlayerJoinListener;
import me.Cashtann.combatRankingSystem.placeholderapi.CRSPlaceholderExpansion;
import me.Cashtann.combatRankingSystem.ranking.PlayerStats;
import me.Cashtann.combatRankingSystem.tasks.SavePlayerStatsTask;
import me.Cashtann.combatRankingSystem.utilities.StringFormatter;
import org.bukkit.Bukkit;
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

    private static HashMap<UUID, PlayerStats> playerStatsCache = new HashMap<>();

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
        BukkitTask savePlayerStats = new SavePlayerStatsTask(playerStatsCache, this).runTaskTimer(this, 0L, 200L);

        // Listeners register
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new ChatMessageListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this, playerStatsCache), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        // PlaceholderAPI register
        new CRSPlaceholderExpansion(this).register();

        // Commands
        getCommand("crs").setExecutor(new CRSCommandManager());

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

    public static HashMap<UUID, PlayerStats> getPlayerStatsCache() {
        return playerStatsCache;
    }

    public static void setPlayerStatsCache(HashMap<UUID, PlayerStats> playerStatsCache) {
        CombatRankingSystem.playerStatsCache = playerStatsCache;
    }

    private void loadOnlinePlayersStats() {
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (Player player : players) {
            PlayersStatsContainer.loadOrCreatePlayerStats(player.getUniqueId());
        }
    }

    public static void saveCacheToConfig() {
        FileConfiguration dataConfig = PlayersStatsContainer.getConfig();
        List<UUID> playersToRemoveFromCache = new ArrayList<>();
        for (UUID uuid : playerStatsCache.keySet()) {
            PlayerStats stats = playerStatsCache.get(uuid);
            PlayersStatsContainer.addPlayerData(uuid,
                    stats.getCombatRating(),
                    stats.getKills(),
                    stats.getDeaths(),
                    stats.getMinedStone(),
                    stats.getDistance_cm(),
                    stats.getPlaytime_t());
            // Check if player is online, otherwise add him to the list of
            // uuids to be removed from cache to keep memory clean
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) {
                playersToRemoveFromCache.add(uuid);
            }
        }
        PlayersStatsContainer.saveConfig();
        // Cleaning memory
        for (UUID uuid : playersToRemoveFromCache) {
            playerStatsCache.remove(uuid);
        }
    }

}
