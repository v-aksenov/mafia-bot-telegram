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
            if (except != null && !except.contains(player)) {
                gameMessageList.add(
                        new GameMessage(player.getUserId(), text, placeholder));
            }
        });
        return gameMessageList;
    }
}
