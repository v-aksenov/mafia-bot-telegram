package com.example.mafia.handlers.command;

import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;
import com.example.mafia.service.GameAdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class InviteCommandHandler implements CommandHandler {

    private final GameAdminService gameAdminService;

    @Override
    public HandleResponse handleCommand(Message message) {
        log.info("обрабатываю команду INVITE");
//        TechResponse invitePlayer;
//        if (message.getReplyToMessage() != null) {
//            invitePlayer = gameAdminService.invitePlayer(message.getChatId(), message.getReplyToMessage().getUserId());
//        } else {
//            invitePlayer = new TechResponse(false, ReplyText.NO_INVITE_TARGET);
//        }
//        return HandleResponse.builder()
//                .success(true)
//                .techResponse(invitePlayer)
//                .requestChatId(message.getChatId())
//                .build();
        return HandleResponse.withError(message.getUserId());
    }
}
