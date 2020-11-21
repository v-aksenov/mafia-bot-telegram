package com.example.mafia.handlers.command;

import com.example.mafia.dto.Command;
import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;
import com.example.mafia.dto.ReplyText;
import com.example.mafia.gaming.AnswerKey;
import com.example.mafia.gaming.AnswerVariant;
import com.example.mafia.gaming.GameMessage;
import com.example.mafia.gaming.GameResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class JoinDeclineCommandHandler implements CommandHandler {

    @Override
    public HandleResponse handleCommand(Message message) {
        log.info("обрабатываю команду JOIN_DECLINE");

        String targetChatId = message.getTargetChatId();

        return HandleResponse.builder()
                .success(true)
                .gameResponse(generateGameResponse(message.getUserId(), targetChatId))
                .requestChatId(message.getUserId())
                .build();
    }

    private GameResponse generateGameResponse(String hostChatId, String targetChatId) {
        return new GameResponse(List.of(messageForTarget(hostChatId), messageForHost(targetChatId)));
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
                ReplyText.JOIN_DECLINED_HOST);
        gameMessage.setAnswerVariantMap(Map.of(0, List.of(answerStartGame, answerFinishGame)));
        return gameMessage;
    }

    private GameMessage messageForTarget(String targetChatId) {
        return new GameMessage(targetChatId, ReplyText.JOIN_ACCEPTED_TARGET);
    }
}
