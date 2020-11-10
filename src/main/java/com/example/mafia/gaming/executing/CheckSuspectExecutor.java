package com.example.mafia.gaming.executing;

import com.example.mafia.gaming.Game;
import com.example.mafia.gaming.GameMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CheckSuspectExecutor {

    public static List<GameMessage> checkSuspect(Game game, String target) {
        log.info("checkSuspect не реализован");
        return List.of();
    }
}
