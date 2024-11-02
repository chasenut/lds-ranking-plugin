package me.Cashtann.combatRankingSystem.commands;

import me.Cashtann.combatRankingSystem.CombatRankingSystem;
import me.Cashtann.combatRankingSystem.ranking.RatingController;
import me.Cashtann.combatRankingSystem.utilities.PlayerMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class CRSReloadCommand extends CRSSubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload config file";
    }

    @Override
    public String getSyntax() {
        return "/crs reload";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length > 0) {
            //sendCommandOutput(true, player, "Reloading config...");
            long timeElapsed = System.currentTimeMillis();

            CombatRankingSystem.getPlugin().reloadConfig();

            timeElapsed = System.currentTimeMillis() - timeElapsed;
            String message = "Configuration reloaded successfully in " + String.valueOf(timeElapsed) + " ms";

            PlayerMessage.sendCommandOutput(true, player, message);
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return List.of();
    }
}
