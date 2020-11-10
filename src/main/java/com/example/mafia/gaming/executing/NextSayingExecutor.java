package com.example.mafia.gaming.executing;

import com.example.mafia.dto.ReplyText;
import com.example.mafia.gaming.Game;
import com.example.mafia.gaming.GameMessage;
import com.example.mafia.gaming.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class NextSayingExecutor {

    public static List<GameMessage> nextSaying(Game game) {
        Player player = game.getCurrentAndIterate();
        if(player != null) {
            return List.of(new GameMessage(game.getLinkedChat(), ReplyText.PLAYER_SAYING, game.getCurrentAndIterate().getName()));
        } else {
            return List.of(new GameMessage(game.getLinkedChat(), ReplyText.LAST_SAID));
        }
    }
}
