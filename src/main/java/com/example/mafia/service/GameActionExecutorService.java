package com.example.mafia.service;

import com.example.mafia.gaming.Game;
import com.example.mafia.gaming.GameMessage;
import com.example.mafia.gaming.executing.Action;
import com.example.mafia.gaming.executing.ActionExecutor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class GameActionExecutorService {

    private final ActionExecutor actionExecutor;
    private final GameDaService gameDaService;

    public List<GameMessage> executeActions(String chatId, List<Action> actionList) {
        Optional<Game> optionalGame = gameDaService.findProcessingGame(chatId);
        Game processingGame = optionalGame.orElseThrow();
        List<GameMessage> gameMessages = actionExecutor.executeActions(processingGame, actionList);
        gameDaService.saveGame(processingGame);
        return gameMessages;
    }

    public List<GameMessage> executeAction(String chatId, Action action) {
        Optional<Game> optionalGame = gameDaService.findProcessingGame(chatId);
        Game processingGame = optionalGame.orElseThrow();
        List<GameMessage> gameMessages = actionExecutor.execute(processingGame, action);
        gameDaService.saveGame(processingGame);
        return gameMessages;
    }
}
