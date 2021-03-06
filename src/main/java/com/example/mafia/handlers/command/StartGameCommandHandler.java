package com.example.mafia.handlers.command;

import com.example.mafia.dto.*;
import com.example.mafia.gaming.AnswerKey;
import com.example.mafia.gaming.AnswerVariant;
import com.example.mafia.gaming.GameMessage;
import com.example.mafia.gaming.GameResponse;
import com.example.mafia.gaming.executing.Action;
import com.example.mafia.service.GameActionExecutorService;
import com.example.mafia.service.GameAdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class StartGameCommandHandler implements CommandHandler {

    private final GameAdminService gameAdminService;
    private final GameActionExecutorService gameActionExecutorService;

    @Override
    public HandleResponse handleCommand(Message message) {
        log.info("обрабатываю команду START_GAME");
        TechResponse techResponse = gameAdminService.startGame(message.getUserId(), message.getUserId());
        if (techResponse.isSuccess()) {
            List<GameMessage> gameMessages = gameActionExecutorService
                    .executeActions(message.getUserId(), List.of(Action.HELLO_ACTION));
            gameMessages.add(generateGameMessageForAdmin(message.getUserId(), techResponse.getReplyText()));
            return HandleResponse.builder()
                    .success(true)
                    .requestChatId(message.getUserId())
                    .gameResponse(new GameResponse(gameMessages))
                    .build();
        }
        return HandleResponse.builder()
                .success(true)
                .requestChatId(message.getUserId())
                .gameResponse(generateNegativeGameResponse(message.getUserId(), techResponse))
                .build();
    }

    private GameMessage generateGameMessageForAdmin(String chatId, ReplyText replyText) {
        AnswerVariant answerFinishGame = new AnswerVariant(
                Map.of(AnswerKey.COMMAND, Command.FINISH_GAME.getCommand()),
                "Завершить игру");

        return new GameMessage(
                chatId,
                replyText,
                Map.of(0, List.of(answerFinishGame)));
    }

    private GameResponse generateNegativeGameResponse(String chatId, TechResponse techResponse) {
        AnswerVariant answerStartGame = new AnswerVariant(
                Map.of(AnswerKey.COMMAND, Command.START_GAME.getCommand()),
                "Начать игру");

        AnswerVariant answerFinishGame = new AnswerVariant(
                Map.of(AnswerKey.COMMAND, Command.FINISH_GAME.getCommand()),
                "Завершить игру");

        GameMessage gameMessage = new GameMessage(
                chatId,
                techResponse.getReplyText(),
                techResponse.getBonusText());
        gameMessage.setAnswerVariantMap(Map.of(0, List.of(answerStartGame, answerFinishGame)));

        return new GameResponse(List.of(gameMessage));
    }
}
