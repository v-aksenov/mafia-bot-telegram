package com.example.mafia.configuration;

import com.example.mafia.dto.Command;
import com.example.mafia.dto.MessageType;
import com.example.mafia.handlers.command.*;
import com.example.mafia.handlers.message.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
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
                                                          JoinCommandHandler joinCommandHandler,
                                                          JoinAcceptCommandHandler joinAcceptCommandHandler,
                                                          JoinDeclineCommandHandler joinDeclineCommandHandler) {
        Map<Command, CommandHandler> map = new HashMap<>();
        map.put(Command.OPEN_GAME, openGameCommandHandler);
        map.put(Command.START_GAME, startGameCommandHandler);
        map.put(Command.INVITE, inviteCommandHandler);
        map.put(Command.FINISH_GAME, finishCommandHandler);
        map.put(Command.NEXT_PLAYER, nextPlayerCommandHandler);
        map.put(Command.KILL, killCommandHandler);
        map.put(Command.START, startPrivateHandler);
        map.put(Command.WANNA_JOIN, wannaJoinCommandHandler);
        map.put(Command.JOIN, joinCommandHandler);
        map.put(Command.JOIN_ACCEPT, joinAcceptCommandHandler);
        map.put(Command.JOIN_DECLINE, joinDeclineCommandHandler);

        return map;
    }


}

