package com.example.mafia.handlers;

import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;
import com.example.mafia.dto.MessageType;
import com.example.mafia.handlers.message.MessageHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class UpdateHandler {

    private final Map<MessageType, MessageHandler> handlerMap;

    public HandleResponse handle(Message message) {
        try {
            MessageHandler messageHandler = handlerMap.get(message.getMessageType());
            log.info("Для сообщения типа [{}] найден handler [{}]", message.getMessageType(), messageHandler);
            return messageHandler.handleMessage(message);
        } catch (Exception e) {
            log.info("при обработке сообщения произшел несчастный случай: {}:\n{}", e.getClass().getName(), e.getMessage());
            return HandleResponse.withError(message.getUserId());
        }
    }
}
