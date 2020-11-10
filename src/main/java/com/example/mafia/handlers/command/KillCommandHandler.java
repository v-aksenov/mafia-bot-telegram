package com.example.mafia.handlers.command;


import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;
import com.example.mafia.dto.ReplyText;
import com.example.mafia.dto.ReplyToMessage;
import com.example.mafia.gaming.GameMessage;
import com.example.mafia.gaming.GameResponse;
import com.example.mafia.gaming.executing.Action;
import com.example.mafia.gaming.executing.ActionType;
import com.example.mafia.service.GameActionExecutorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class KillCommandHandler implements CommandHandler {

    private final GameActionExecutorService gameActionExecutorService;

    @Override
    public HandleResponse handleCommand(Message message) {
        ReplyToMessage replyToMessage = message.getReplyToMessage();
        log.info("обрабатываю команду KILL. Цель [{}]", replyToMessage.getUserId());
        Action action = Action.builder()
                .actionType(ActionType.KILL)
                .initiator(message.getUserId())
                .target(replyToMessage.getUserId())
                .build();
        List<GameMessage> gameMessages = gameActionExecutorService.executeAction(replyToMessage.getChatId(), action);
        if (!gameMessages.get(0).getReplyText().equals(ReplyText.CITIZEN_ALREADY_DEAD) && needToContinue(gameMessages)) {
            gameMessages.addAll(gameActionExecutorService.executeAction(replyToMessage.getChatId(), Action.CHANGE_DAY_STATE));
        }
        return HandleResponse.builder()
                .success(true)
                .gameResponse(new GameResponse(gameMessages))
                .build();
    }

    private boolean needToContinue(List<GameMessage> gameMessageList) {
        for (GameMessage gameMessage : gameMessageList) {
            if (gameMessage.getReplyText().equals(ReplyText.CITIZEN_WON) || gameMessage.getReplyText().equals(ReplyText.MAFIA_WON)) {
                return false;
            }
        }

        return true;
    }
}
