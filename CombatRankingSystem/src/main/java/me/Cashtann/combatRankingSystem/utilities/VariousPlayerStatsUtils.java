package me.Cashtann.combatRankingSystem.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.UUID;

public class VariousPlayerStatsUtils {

    public static int getPlayerWalkedDistanceByFoot(Player player) {
        return player.getStatistic(Statistic.CROUCH_ONE_CM) + player.getStatistic(Statistic.WALK_ONE_CM) + player.getStatistic(Statistic.SPRINT_ONE_CM);
    }

    public static int getPlayerWalkedDistanceByFoot(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return player.getStatistic(Statistic.CROUCH_ONE_CM) + player.getStatistic(Statistic.WALK_ONE_CM) + player.getStatistic(Statistic.SPRINT_ONE_CM);
    }

}
