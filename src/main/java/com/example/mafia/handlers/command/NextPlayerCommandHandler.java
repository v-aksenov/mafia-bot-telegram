package com.example.mafia.handlers.command;

import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;
import com.example.mafia.dto.ReplyText;
import com.example.mafia.gaming.executing.Action;
import com.example.mafia.gaming.GameMessage;
import com.example.mafia.gaming.GameResponse;
import com.example.mafia.service.GameActionExecutorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class NextPlayerCommandHandler implements CommandHandler {

    private final GameActionExecutorService gameActionExecutorService;

    @Override
    public HandleResponse handleCommand(Message message) {
        log.info("обрабатываю команду NEXT_PLAYER");
        List<GameMessage> gameMessages = gameActionExecutorService
                .executeActions(message.getChatId(), List.of(Action.NEXT_PLAYER_SAYING));
        if(gameMessages.get(0).getReplyText().equals(ReplyText.LAST_SAID)) {
            gameMessages.addAll(gameActionExecutorService
                    .executeActions(message.getChatId(), List.of(Action.CHANGE_DAY_STATE)));
        }
        return HandleResponse.builder()
                .success(true)
                .gameResponse(new GameResponse(gameMessages))
                .build();
    }
}
