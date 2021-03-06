package com.example.mafia.handlers.command;

import com.example.mafia.dto.Command;
import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;
import com.example.mafia.dto.TechResponse;
import com.example.mafia.gaming.*;
import com.example.mafia.service.GameAdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class OpenGameCommandHandler implements CommandHandler {

    private final GameAdminService gameAdminService;

    @Override
    public HandleResponse handleCommand(Message message) {
        log.info("обрабатываю команду OPEN_GAME");
        Player player = new Player();
        player.setName(message.getFirstName());
        player.setUserId(message.getUserId());

        TechResponse techResponse = gameAdminService.openGame(message.getUserId(), player);
        log.info("Игра создана успешно: {}", techResponse.isSuccess());

        return HandleResponse.builder()
                .success(true)
                .gameResponse(generateGameResponse(message.getUserId(), techResponse))
                .requestChatId(message.getUserId())
                .build();
    }

    private GameResponse generateGameResponse(String chatId, TechResponse techResponse) {
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
