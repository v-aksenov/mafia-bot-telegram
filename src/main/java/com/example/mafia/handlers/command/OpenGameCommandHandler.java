package com.example.mafia.handlers.command;

import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;
import com.example.mafia.dto.TechResponse;
import com.example.mafia.gaming.Player;
import com.example.mafia.service.GameAdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OpenGameCommandHandler implements CommandHandler {

    public static final String OPEN_GAME = "Игра открыта";

    private final GameAdminService gameAdminService;

    @Override
    public HandleResponse handleCommand(Message message) {
        log.info("обрабатываю команду OPEN_GAME");
        Player player = new Player();
        player.setName(message.getFirstName() + " " + message.getLastName());
        player.setUserId(message.getUserId());

        TechResponse techResponse = gameAdminService.openGame(message.getChatId(), player);
        log.info("Игра создана успешно: {}", techResponse.isSuccess());

        return HandleResponse.builder()
                .success(true)
                .techResponse(techResponse)
                .requestChatId(message.getChatId())
                .build();
    }
}
