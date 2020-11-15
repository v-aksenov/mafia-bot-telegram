package com.example.mafia.handlers.command;

import com.example.mafia.dto.*;
import com.example.mafia.gaming.*;
import com.example.mafia.service.GameAdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class JoinCommandHandler implements CommandHandler {

    private final GameAdminService gameAdminService;

    @Override
    public HandleResponse handleCommand(Message message) {
        log.info("обрабатываю команду JOIN");

        String text = message.getText();
        String gameToJoin = StringUtils.substringAfter(text, Command.JOIN.getCommand() + " ");
        Long gameToJoinLong = Long.valueOf(gameToJoin);
        TechResponse techResponse = gameAdminService.inviteCandidate(message.getUserId(), gameToJoinLong);

        if (techResponse.isSuccess()) {
            Player player = new Player();
            player.setName(message.getFirstName());
            player.setUserId(message.getUserId());
            return HandleResponse.builder()
                    .success(true)
                    .techResponse(techResponse)
                    .requestChatId(message.getUserId())
                    .gameResponse(generateGameResponse(techResponse.getHostChatId(), player))
                    .build();
        }

        return HandleResponse.builder()
                .success(true)
                .techResponse(new TechResponse(true, ReplyText.HOST_NOT_FOUND, gameToJoin))
                .requestChatId(message.getUserId())
                .build();
    }


    private GameResponse generateGameResponse(String hostChatId, Player player) {
        AnswerVariant inviteAcceptAnswer = new AnswerVariant(
                Map.of(AnswerKey.COMMAND, Command.JOIN_ACCEPT.getCommand(),
                        AnswerKey.TARGET, player.getUserId(),
                        AnswerKey.NAME, player.getName()),
                "Принять " + player.getName()
        );
        AnswerVariant inviteDeclineAnswer = new AnswerVariant(
                Map.of(AnswerKey.COMMAND, Command.JOIN_DECLINE.getCommand(),
                        AnswerKey.TARGET, player.getUserId()),
                "Отказать " + player.getName()
        );
        GameMessage gameMessage = new GameMessage(hostChatId, ReplyText.HOST_WANNA_INVITE_NOTIFICATION, player.getName());
        gameMessage.setAnswerVariantList(List.of(inviteAcceptAnswer, inviteDeclineAnswer));
        return new GameResponse(List.of(gameMessage));
    }
}
