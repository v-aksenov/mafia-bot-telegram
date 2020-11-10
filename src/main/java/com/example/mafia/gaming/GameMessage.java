package com.example.mafia.gaming;

import com.example.mafia.dto.ReplyText;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GameMessage {

    private final String chatId;
    private final ReplyText replyText;
    private String textInjection;
    private List<AnswerVariant> answerVariantList;

    public GameMessage(String chatId, ReplyText replyText, String textInjection) {
        this.chatId = chatId;
        this.replyText = replyText;
        this.textInjection = textInjection;
    }

    public GameMessage(String chatId, ReplyText replyText, List<AnswerVariant> answerVariantList) {
        this.chatId = chatId;
        this.replyText = replyText;
        this.answerVariantList = answerVariantList;
    }
}
