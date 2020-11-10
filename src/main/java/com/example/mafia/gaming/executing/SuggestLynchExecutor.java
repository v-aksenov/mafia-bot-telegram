package com.example.mafia.gaming.executing;

import com.example.mafia.dto.ReplyText;
import com.example.mafia.gaming.Game;
import com.example.mafia.gaming.GameMessage;
import com.example.mafia.gaming.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SuggestLynchExecutor {

    public static List<GameMessage> suggestLynch(Game game, String target) {
        Player lynchTarget = game.getPlayerWithId(target);
        return List.of(new GameMessage(game.getLinkedChat(), ReplyText.LYNCH_SUGGESTION, lynchTarget.getName()));
    }
}
