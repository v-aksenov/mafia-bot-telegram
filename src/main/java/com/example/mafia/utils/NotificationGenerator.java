package com.example.mafia.utils;

import com.example.mafia.dto.ReplyText;
import com.example.mafia.gaming.GameMessage;
import com.example.mafia.gaming.Player;

import java.util.ArrayList;
import java.util.List;

public class NotificationGenerator {

    public static List<GameMessage> generate(List<Player> playerList, List<Player> except, ReplyText text) {
        return generate(playerList, except, text, null);
    }

    public static List<GameMessage> generate(List<Player> playerList, ReplyText text) {
        return generate(playerList, null, text, null);
    }

    public static List<GameMessage> generate(List<Player> playerList, ReplyText text, String placeholder) {
        return generate(playerList, null, text, placeholder);
    }

    public static List<GameMessage> generate(List<Player> playerList, List<Player> except, ReplyText text, String placeholder) {
        List<GameMessage> gameMessageList = new ArrayList<>();
        playerList.forEach(player -> {
            if (allowToAdd(player, except)) {
                gameMessageList.add(
                        new GameMessage(player.getUserId(), text, placeholder));
            }
        });
        return gameMessageList;
    }

    private static boolean allowToAdd(Player player, List<Player> except) {
        if (except == null) {
            return true;
        }

        return !except.contains(player);
    }
}
