package com.example.mafia.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Component
@PropertySource("classpath:bot.properties")
public class BotCredentials {

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;
}
