package com.example.mafia.handlers.message;

import com.example.mafia.dto.Command;
import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;
import com.example.mafia.handlers.command.CommandHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class ChatCommandHandler implements MessageHandler {

    private final Map<Command, CommandHandler> commandHandlerMap;

    @Override
    public HandleResponse handleMessage(Message message) {
        if(message.getCommand() == null) {
            log.info("В сообщении [{}] не найдено знакомой команды", message.getText());
            throw new IllegalArgumentException("Unknown command: " + message.getText());
        }

        CommandHandler commandHandler = commandHandlerMap.get(message.getCommand());
        log.info("Команду [{}] выполняет handler [{}]", message.getCommand(), commandHandler);

        return commandHandler.handleCommand(message);
    }
}
