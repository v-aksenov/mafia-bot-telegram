package com.example.mafia.configuration;

import com.example.mafia.dto.Command;
import com.example.mafia.dto.MessageType;
import com.example.mafia.handlers.command.*;
import com.example.mafia.handlers.message.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class HandlersConfiguration {

    @Bean
    public Map<MessageType, MessageHandler> handlerMap(ChatCreateHandler chatCreateHandler,
                                                       PrivateTextHandler privateTextHandler,
                                                       ChatCommandHandler chatCommandHandler,
                                                       ChatTextMessageHandler chatTextMessageHandler) {
        return Map.of(
                MessageType.CHAT_CREATE, chatCreateHandler,
                MessageType.CHAT_INVITE, chatCreateHandler,
                MessageType.PRIVATE_TEXT, privateTextHandler,
                MessageType.CHAT_COMMAND, chatCommandHandler,
                MessageType.PRIVATE_COMMAND, chatCommandHandler,
                MessageType.CHAT_TEXT, chatTextMessageHandler
        );
    }

    @Bean
    public Map<Command, CommandHandler> commandHandlerMap(OpenGameCommandHandler openGameCommandHandler,
                                                          StartGameCommandHandler startGameCommandHandler,
                                                          InviteCommandHandler inviteCommandHandler,
                                                          FinishCommandHandler finishCommandHandler,
                                                          NextPlayerCommandHandler nextPlayerCommandHandler,
                                                          KillCommandHandler killCommandHandler,
                                                          StartPrivateHandler startPrivateHandler,
                                                          WannaJoinCommandHandler wannaJoinCommandHandler,
                                                          JoinCommandHandler joinCommandHandler) {
        return Map.of(
                Command.OPEN_GAME, openGameCommandHandler,
                Command.START_GAME, startGameCommandHandler,
                Command.INVITE, inviteCommandHandler,
                Command.FINISH_GAME, finishCommandHandler,
                Command.NEXT_PLAYER, nextPlayerCommandHandler,
                Command.KILL, killCommandHandler,
                Command.START, startPrivateHandler,
                Command.WANNA_JOIN, wannaJoinCommandHandler,
                Command.JOIN, joinCommandHandler
        );
    }


}

