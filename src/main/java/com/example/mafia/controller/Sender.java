package com.example.mafia.controller;

import com.example.mafia.configuration.BotCredentials;
import com.example.mafia.dto.HandleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Service
public class Sender extends DefaultAbsSender {

    private final AnswerGenerator answerGenerator;
    private final BotCredentials botCredentials;

    protected Sender(BotCredentials credentials, AnswerGenerator answerGenerator) {
        super(new DefaultBotOptions());
        this.botCredentials = credentials;
        this.answerGenerator = answerGenerator;
    }

    @Override
    public String getBotToken() {
        return botCredentials.getToken();
    }

    public void answerToUpdate(HandleResponse response) {
        List<SendMessage> messageList = answerGenerator.getMessage(response);
        for (SendMessage m : messageList) {
            send(m);
        }
    }

    private void send(SendMessage message) {
        try {
            log.info("Отправляю [{}] в чат [{}]", message.getText(), message.getChatId());
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
