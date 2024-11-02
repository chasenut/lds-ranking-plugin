package me.Cashtann.combatRankingSystem;

import me.Cashtann.combatRankingSystem.commands.CRSCommandManager;
import me.Cashtann.combatRankingSystem.listeners.ChatMessageListener;
import me.Cashtann.combatRankingSystem.listeners.PlayerDeathListener;
import me.Cashtann.combatRankingSystem.listeners.PlayerJoinListener;
import me.Cashtann.combatRankingSystem.placeholderapi.CRSPlaceholderExpansion;
import me.Cashtann.combatRankingSystem.utilities.StringFormatter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class CombatRankingSystem extends JavaPlugin implements Listener {

    private static CombatRankingSystem plugin;

    private static String commandSuccessOutputPrefix;
    private static String commandFailedOutputPrefix;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Config load and save
        //this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();


        // Listeners register
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new ChatMessageListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        // PlaceholderAPI register
        new CRSPlaceholderExpansion(this).register();

        // Commands
        getCommand("crs").setExecutor(new CRSCommandManager());

        plugin = this;
        System.out.println("============================= ");
        System.out.println("Combat Ranking System Loaded! ");
        System.out.println("============================= ");

        getServer().broadcastMessage(StringFormatter.formatString(" \n\n       &dCombat Ranking System powered by Cashtann\n\n"));

        commandSuccessOutputPrefix = getPlugin().getConfig().getString("commands.prefix-success");
        commandFailedOutputPrefix = getPlugin().getConfig().getString("commands.prefix-failed");

    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(StringFormatter.formatString("&dGracz &5" + event.getPlayer().getName() + " &ddołączył na serwer! "));
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
}
