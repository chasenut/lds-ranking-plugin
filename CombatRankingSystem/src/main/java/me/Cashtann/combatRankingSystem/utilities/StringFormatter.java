package me.Cashtann.combatRankingSystem.utilities;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFormatter {

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
    public static String formatString(String msg) {
        // We hope that the user is using Minecraft above 1.16
        // hex colors
        Matcher match = pattern.matcher(msg);
        while (match.find()) {
            String color = msg.substring(match.start(), match.end());
            msg = msg.replace(color, ChatColor.of(color) + "");
            match = pattern.matcher(msg);
        }
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
