package com.example.mafia.handlers.command;

import com.example.mafia.dto.Command;
import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;
import com.example.mafia.dto.ReplyText;
import com.example.mafia.dto.TechResponse;
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
public class StartPrivateHandler implements CommandHandler {

    @Override
    public HandleResponse handleCommand(Message message) {
        log.info("обрабатываю команду START");
        return HandleResponse.builder()
                .success(true)
                .requestChatId(message.getChatId())
                .recipientChatId(message.getChatId())
                .gameResponse(generateGameResponse(message.getUserId()))
                .build();
    }

    private GameResponse generateGameResponse(String chatId) {
        AnswerVariant answerOpenGame = new AnswerVariant(
                Map.of(AnswerKey.COMMAND, Command.OPEN_GAME.getCommand()),
                "Открыть новую игру"
        );
        GameMessage gameMessage = new GameMessage(chatId, ReplyText.START_PRIVATE, List.of(answerOpenGame));
        return new GameResponse(List.of(gameMessage));
    }
}
