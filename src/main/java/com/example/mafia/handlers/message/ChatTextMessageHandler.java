package com.example.mafia.handlers.message;

import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;
import com.example.mafia.gaming.Player;
import com.example.mafia.service.PlayerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ChatTextMessageHandler implements MessageHandler {

    private final PlayerService playerService;

    @Override
    public HandleResponse handleMessage(Message message) {
        log.info("ChatCreateHandler обрабатывает message");
        playerService.addPlayer(playerFromMessage(message));
        return HandleResponse.builder()
                .recipientChatId(message.getChatId())
                .requestChatId(message.getChatId())
                .success(true)
                .build();
    }

    private Player playerFromMessage(Message message) {
        Player player = new Player();
        player.setName(message.getFirstName() + " " + message.getLastName());
        player.setUserId(message.getUserId());
        return player;
    }
}
