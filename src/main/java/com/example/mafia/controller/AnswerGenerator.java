package com.example.mafia.controller;

import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.ReplyText;
import com.example.mafia.dto.TechResponse;
import com.example.mafia.gaming.AnswerVariant;
import com.example.mafia.gaming.GameMessage;
import com.example.mafia.utils.CallbackEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AnswerGenerator {

    public static final String DEFAULT_OK = "Запрос обработан успешно";
    public static final String DEFAULT_ERROR = "Запрос не обработан";

    public List<SendMessage> getMessage(HandleResponse response) {
        if (!response.isSuccess()) {
            return List.of(errorMessage(response.getRequestChatId()));
        }

        List<SendMessage> sendMessageList = new ArrayList<>(parseResponse(response));

        return sendMessageList.isEmpty() ?
                List.of(defaultSuccess(response.getRequestChatId())) :
                sendMessageList;
    }

    private SendMessage defaultSuccess(String requestChatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(requestChatId);
        sendMessage.setText(DEFAULT_OK);
        return sendMessage;
    }

    private List<SendMessage> parseResponse(HandleResponse response) {
        List<SendMessage> sendMessageList = new ArrayList<>();
        if (response.getTechResponse() != null) {
            TechResponse techResponse = response.getTechResponse();
            SendMessage sendMessage = new SendMessage(
                    response.getRequestChatId(),
                    techResponse.getReplyText().getText());

            if(ReplyText.REQUIRED_REPLY.contains(techResponse.getReplyText())) {
                log.info("Добавляю большие кнопки для команды [{}]", techResponse.getReplyText().getText());
                sendMessage.setReplyMarkup(BigButtonKeyboardReply.getKeyboardForText(techResponse.getReplyText()));
            }

            sendMessageList.add(sendMessage);
        }

        if (response.getGameResponse() != null) {
            for (GameMessage message : response.getGameResponse().getMessageList()) {
                SendMessage sendMessage = generateMessage(message);
                if (!CollectionUtils.isEmpty(message.getAnswerVariantList())) {
                    log.info("Добавляю варианты ответа");
                    sendMessage.setReplyMarkup(generateReply(message.getAnswerVariantList()));
                }
                sendMessageList.add(sendMessage);
            }
        }

        return sendMessageList;
    }

    private ReplyKeyboard generateReply(List<AnswerVariant> answerVariantList) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(List.of(
                answerVariantList.stream()
                        .map(answerVariant -> new InlineKeyboardButton()
                                .setText(answerVariant.getFrontText())
                                .setCallbackData(CallbackEncoder.encode(answerVariant.getHiddenCommand())))
                        .collect(Collectors.toList())
        ));
        return inlineKeyboardMarkup;
    }

    private SendMessage generateMessage(GameMessage message) {
        if (message.getTextInjection() != null) {
            return new SendMessage(
                    message.getChatId(),
                    String.format(message.getReplyText().getText(), message.getTextInjection()));
        } else {
            return new SendMessage(message.getChatId(), message.getReplyText().getText());
        }
    }

    private SendMessage errorMessage(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(DEFAULT_ERROR);
        return sendMessage;
    }
}
