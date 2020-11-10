package com.example.mafia.handlers.command;

import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;
import com.example.mafia.dto.TechResponse;
import com.example.mafia.gaming.executing.Action;
import com.example.mafia.gaming.GameMessage;
import com.example.mafia.gaming.GameResponse;
import com.example.mafia.service.GameActionExecutorService;
import com.example.mafia.service.GameAdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class StartGameCommandHandler implements CommandHandler {

    private final GameAdminService gameAdminService;
    private final GameActionExecutorService gameActionExecutorService;

    @Override
    public HandleResponse handleCommand(Message message) {
        log.info("обрабатываю команду START_GAME");
        TechResponse techResponse = gameAdminService.startGame(message.getChatId(), message.getUserId());
        List<GameMessage> gameMessages = gameActionExecutorService
                .executeActions(message.getChatId(), List.of(Action.HELLO_ACTION));
        return HandleResponse.builder()
                .success(true)
                .techResponse(techResponse)
                .requestChatId(message.getChatId())
                .gameResponse(new GameResponse(gameMessages))
                .build();
    }
}
