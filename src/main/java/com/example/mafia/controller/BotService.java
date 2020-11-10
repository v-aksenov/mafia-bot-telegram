package com.example.mafia.controller;

import com.example.mafia.configuration.BotCredentials;
import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;
import com.example.mafia.handlers.UpdateHandler;
import com.example.mafia.utils.MessageParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

@Slf4j
@Service
public class BotService implements LongPollingBot {

    private final BotOptions botOptions = new DefaultBotOptions();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final UpdateHandler updateHandler;
    private final Sender sender;
    private final BotCredentials botCredentials;
    private final MessageParser parser;

    public BotService(UpdateHandler updateHandler, Sender sender, BotCredentials botCredentials) {
        this.updateHandler = updateHandler;
        this.sender = sender;
        this.botCredentials = botCredentials;
        this.parser = new MessageParser(botCredentials.getUsername());;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        log.info(objectMapper.writeValueAsString(update));
        Message message = parser.parse(update);
        HandleResponse handleResponse = updateHandler.handle(message);
        sender.answerToUpdate(handleResponse);
    }

    @Override
    public String getBotUsername() {
        return botCredentials.getUsername();
    }

    @Override
    public String getBotToken() {
        return botCredentials.getToken();
    }

    @Override
    public BotOptions getOptions() {
        return botOptions;
    }

    @Override
    public void clearWebhook() {

    }

}
