package me.Cashtann.combatRankingSystem.commands;

import me.Cashtann.combatRankingSystem.utilities.PlayerMessage;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CRSHelpCommand extends CRSSubCommand {

    private ArrayList<CRSSubCommand> subcommands;

    public CRSHelpCommand(ArrayList<CRSSubCommand> subcommands) {
        this.subcommands = subcommands;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Help command for CRS plugin";
    }

    @Override
    public String getSyntax() {
        return "/crs help";
    }

    @Override
    public void perform(Player player, String[] args) {
        PlayerMessage.sendCommandOutput(true, player, "-------------------------------");
        PlayerMessage.sendCommandOutput(true, player, " All available commands:");
        for (int i = 0; i < getSubcommands().size(); i++) {
            String commandMessage = "  ▪ " + getSubcommands().get(i).getSyntax() + " - " + getSubcommands().get(i).getDescription();
            PlayerMessage.sendCommandOutput(true, player, commandMessage);
        }
        PlayerMessage.sendCommandOutput(true, player, "-------------------------------");
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return List.of();
    }


    public ArrayList<CRSSubCommand> getSubcommands() {
        return subcommands;
    }
}
