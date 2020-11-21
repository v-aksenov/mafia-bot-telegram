package com.example.mafia.gaming.executing;

import com.example.mafia.dto.ReplyText;
import com.example.mafia.gaming.Game;
import com.example.mafia.gaming.GameMessage;
import com.example.mafia.gaming.Player;
import com.example.mafia.gaming.Role;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SayHelloExecutor {

    public static List<GameMessage> sayHelloAction(Game game) {
        List<GameMessage> helloMessages = new ArrayList<>();
        for (Player player : game.getPlayerList()) {
            helloMessages.add(new GameMessage(player.getUserId(), player.getRole().equals(Role.MAFIA) ?
                    ReplyText.YOU_MAFIA : ReplyText.YOU_CITIZEN));
        }
        helloMessages.add(new GameMessage(game.getLinkedChat(), ReplyText.FIRST_DAY_STARTED));

        game.startDiscuss();

        List<GameMessage> nextSaying = NextSayingExecutor.nextSaying(game);

        helloMessages.addAll(nextSaying);
        return helloMessages;
    }
}
