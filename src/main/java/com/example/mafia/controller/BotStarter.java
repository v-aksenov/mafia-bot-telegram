package com.example.mafia.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;

@Component
@AllArgsConstructor
public class BotStarter implements InitializingBean {

    private final BotService botService;

    public void afterPropertiesSet() {
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            BotSession session = api.registerBot(botService);
            if (!session.isRunning()) {
                session.start();
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
