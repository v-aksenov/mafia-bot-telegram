package com.example.mafia.gaming.executing;

import com.example.mafia.dto.ReplyText;
import com.example.mafia.gaming.Game;
import com.example.mafia.gaming.GameMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ToDayExecutor {

    public static List<GameMessage> toDay(Game game) {
        return List.of(new GameMessage(game.getLinkedChat(), ReplyText.DAY_STARTED));
    }
}
