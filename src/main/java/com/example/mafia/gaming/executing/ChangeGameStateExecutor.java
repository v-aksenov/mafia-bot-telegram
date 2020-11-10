package com.example.mafia.gaming.executing;

import com.example.mafia.gaming.DayState;
import com.example.mafia.gaming.Game;
import com.example.mafia.gaming.GameMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ChangeGameStateExecutor {

    public static List<GameMessage> changeGameState(Game game) {
        if (DayState.DAY.equals(game.getDayState())) {
            return ToNightExecutor.toNight(game);
        }
        return ToDayExecutor.toDay(game);
    }

}
