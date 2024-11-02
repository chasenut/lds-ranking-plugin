package me.Cashtann.combatRankingSystem.commands;

import me.Cashtann.combatRankingSystem.utilities.PlayerMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CRSCommandManager implements TabExecutor {

    private ArrayList<CRSSubCommand> subcommands = new ArrayList<>();

    private final CRSHelpCommand helpCommand = new CRSHelpCommand(subcommands);

    public CRSCommandManager() {
        subcommands.add(new CRSInfoCommand());
        subcommands.add(new CRSReloadCommand());
        subcommands.add(new CRSSetCommand());
        subcommands.add(helpCommand);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player player) {
            if (strings.length > 0) {
                for (int i = 0; i < subcommands.size(); i++) {
                    if (strings[0].equalsIgnoreCase(getSubcommands().get(i).getName())) {
                        getSubcommands().get(i).perform(player, strings);
                    }
                }
            } else {
                helpCommand.perform(player, strings);
            }
        }
        return true;
    }

    public ArrayList<CRSSubCommand> getSubcommands() {
        return subcommands;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (strings.length == 1) {
            ArrayList<String> subcommandsArguments = new ArrayList<>();
            for (int i = 0; i < getSubcommands().size(); i++) {
                subcommandsArguments.add(getSubcommands().get(i).getName());
            }
            return subcommandsArguments;
        } else if (strings.length >= 2) {
            for (int i = 0; i < subcommands.size(); i++) {
                if (strings[0].equalsIgnoreCase(getSubcommands().get(i).getName())) {
                    return getSubcommands().get(i).getSubcommandArguments((Player) commandSender, strings);
                }
            }
        }

        return List.of();
    }
}
