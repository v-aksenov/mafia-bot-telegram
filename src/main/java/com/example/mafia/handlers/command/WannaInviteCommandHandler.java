package com.example.mafia.handlers.command;

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
public class WannaInviteCommandHandler implements CommandHandler {

    @Override
    public HandleResponse handleCommand(Message message) {
        log.info("обрабатываю команду WANNA_INVITE");
        return HandleResponse.builder()
                .success(true)
                .techResponse(new TechResponse(true, ReplyText.WANNA_INVITE))
                .requestChatId(message.getUserId())
                .build();
    }
}
