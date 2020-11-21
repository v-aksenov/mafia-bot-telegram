package com.example.mafia.handlers.command;

import com.example.mafia.dto.*;
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
public class JoinAcceptCommandHandler implements CommandHandler {

    private final GameAdminService gameAdminService;

    @Override
    public HandleResponse handleCommand(Message message) {
        log.info("обрабатываю команду JOIN_ACCEPT");
        Player player = new Player();
        player.setUserId(message.getTargetChatId());
        player.setName(message.getTargetName());
        TechResponse techResponse = gameAdminService.invitePlayer(message.getUserId(), player);

        if (techResponse.isSuccess()) {
            return HandleResponse.builder()
                    .success(true)
                    .gameResponse(generateGameResponse(message.getUserId(), techResponse, player.getUserId()))
                    .build();
        }

        return HandleResponse.builder()
                .success(true)
                .techResponse(new TechResponse(true, ReplyText.JOIN_DECLINED_HOST))
                .requestChatId(message.getUserId())
                .build();
    }

    private GameResponse generateGameResponse(String hostChatId, TechResponse techResponse, String targetChatId) {
        if (techResponse.isSuccess()) {
            return new GameResponse(List.of(messageForTarget(hostChatId), messageForHost(targetChatId)));
        } else {
            return new GameResponse(List.of(new GameMessage(hostChatId, ReplyText.INVITE_ERROR)));
        }
    }

    private GameMessage messageForHost(String hostChatId) {
        AnswerVariant answerStartGame = new AnswerVariant(
                Map.of(AnswerKey.COMMAND, Command.START_GAME.getCommand()),
                "Начать игру");

        AnswerVariant answerFinishGame = new AnswerVariant(
                Map.of(AnswerKey.COMMAND, Command.FINISH_GAME.getCommand()),
                "Завершить игру");

        GameMessage gameMessage = new GameMessage(
                hostChatId,
                ReplyText.JOIN_ACCEPTED_HOST);
        gameMessage.setAnswerVariantMap(Map.of(0, List.of(answerStartGame, answerFinishGame)));
        return gameMessage;
    }

    private GameMessage messageForTarget(String targetChatId) {
        return new GameMessage(targetChatId, ReplyText.JOIN_ACCEPTED_TARGET);
    }
}