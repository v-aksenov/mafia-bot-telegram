package com.example.mafia.gaming;

import com.example.mafia.dto.ReplyText;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GameMessage {

    private final String chatId;
    private final ReplyText replyText;
    private String textInjection;
    private Map<Integer, List<AnswerVariant>> answerVariantMap;

    public GameMessage(String chatId, ReplyText replyText, String textInjection) {
        this.chatId = chatId;
        this.replyText = replyText;
        this.textInjection = textInjection;
    }

    public GameMessage(String chatId, ReplyText replyText, Map<Integer, List<AnswerVariant>> answerVariantMap) {
        this.chatId = chatId;
        this.replyText = replyText;
        this.answerVariantMap = answerVariantMap;
    }
}
