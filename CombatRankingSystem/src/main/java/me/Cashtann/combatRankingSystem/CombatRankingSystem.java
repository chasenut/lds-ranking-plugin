package me.Cashtann.combatRankingSystem;

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

    @Override
    public void onEnable() {
        // Plugin startup logic


        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new ChatMessageListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().broadcastMessage(StringFormatter.formatString(" \n\n       &dCombat Ranking System powered by Cashtann\n\n"));


        new CRSPlaceholderExpansion(this).register();

        plugin = this;
        System.out.println("============================= ");
        System.out.println("Combat Ranking System Loaded! ");
        System.out.println("============================= ");

    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(StringFormatter.formatString("&dGracz &5" + event.getPlayer().getName() + " &ddołączył na serwer! "));
    }


    public static CombatRankingSystem getPlugin() {
        return plugin;
    }
}
