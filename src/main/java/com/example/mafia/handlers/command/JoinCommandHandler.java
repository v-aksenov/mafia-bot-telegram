package com.example.mafia.handlers.command;

import com.example.mafia.dto.*;
import com.example.mafia.gaming.*;
import com.example.mafia.service.GameAdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public HandleResponse handleCommand(Message message) {
        log.info("обрабатываю команду JOIN");

        String text = message.getText();
        String gameToJoin = StringUtils.substringAfter(text, Command.JOIN.getCommand() + " ");
        Long gameToJoinLong = Long.valueOf(gameToJoin);
        TechResponse techResponse = gameAdminService.inviteCandidate(message.getUserId(), gameToJoinLong);

        if (techResponse.isSuccess()) {
            Player player = new Player();
            player.setName(message.getFirstName() + " " + message.getLastName());
            player.setUserId(message.getUserId());
            try {
                return HandleResponse.builder()
                        .success(true)
                        .techResponse(techResponse)
                        .requestChatId(message.getUserId())
                        .gameResponse(generateGameResponse(techResponse.getHostChatId(), player))
                        .build();
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException(e);
            }
        }

        return HandleResponse.builder()
                .success(true)
                .techResponse(new TechResponse(true, ReplyText.HOST_NOT_FOUND, gameToJoin))
                .requestChatId(message.getUserId())
                .build();
    }


    private GameResponse generateGameResponse(String hostChatId, Player player) throws JsonProcessingException {
        AnswerVariant inviteAcceptAnswer = new AnswerVariant(
                Map.of(AnswerKey.COMMAND, Command.JOIN_ACCEPT.getCommand(),
                        AnswerKey.TARGET, mapper.writeValueAsString(player)),
                "Принять " + player.getName()
        );
        AnswerVariant inviteDeclineAnswer = new AnswerVariant(
                Map.of(AnswerKey.COMMAND, Command.JOIN_DECLINE.getCommand(),
                        AnswerKey.TARGET, player.getUserId()),
                "Отказать " + player.getName()
        );
        GameMessage gameMessage = new GameMessage(hostChatId, ReplyText.HOST_WANNA_INVITE_NOTIFICATION, List.of(inviteAcceptAnswer, inviteDeclineAnswer));
        return new GameResponse(List.of(gameMessage));
    }
}
