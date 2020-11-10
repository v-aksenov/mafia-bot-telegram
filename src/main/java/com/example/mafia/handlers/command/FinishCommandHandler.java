package com.example.mafia.handlers.command;

import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;
import com.example.mafia.dto.TechResponse;
import com.example.mafia.service.GameAdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class FinishCommandHandler implements CommandHandler {
    private final GameAdminService gameAdminService;

    @Override
    public HandleResponse handleCommand(Message message) {
        log.info("обрабатываю команду FINISH_GAME");
        TechResponse techResponse = gameAdminService.finishGame(message.getChatId(), message.getUserId());
        log.info("Завершение игры успешно: [{}]", techResponse.isSuccess());
        return HandleResponse.builder()
                .success(true)
                .techResponse(techResponse)
                .requestChatId(message.getChatId())
                .build();
    }
}
