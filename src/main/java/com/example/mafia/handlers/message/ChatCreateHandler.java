package com.example.mafia.handlers.message;

import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;
import com.example.mafia.dto.ReplyText;
import com.example.mafia.dto.TechResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ChatCreateHandler implements MessageHandler {

    @Override
    public HandleResponse handleMessage(Message message) {
        log.info("ChatCreateHandler обрабатывает message");

        return HandleResponse.builder()
                .success(true)
                .requestChatId(message.getChatId())
                .techResponse(new TechResponse(true, ReplyText.BOT_WAS_INVITED))
                .build();
    }
}
