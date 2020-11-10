package com.example.mafia.gaming.executing;

import com.example.mafia.dto.ReplyText;
import com.example.mafia.gaming.Game;
import com.example.mafia.gaming.GameMessage;
import com.example.mafia.gaming.Player;
import com.example.mafia.gaming.Role;

import java.util.ArrayList;
import java.util.List;

public class SayHelloExecutor {

    public static List<GameMessage> sayHelloAction(Game game) {
        List<GameMessage> helloMessages = new ArrayList<>();
        for (Player player : game.getPlayerList()) {
            helloMessages.add(new GameMessage(player.getUserId(), player.getRole().equals(Role.MAFIA) ?
                    ReplyText.YOU_MAFIA : ReplyText.YOU_CITIZEN));
        }
        helloMessages.add(new GameMessage(game.getLinkedChat(), ReplyText.FIRST_DAY_STARTED));

        game.startDiscuss();
        helloMessages.add(new GameMessage(game.getLinkedChat(), ReplyText.PLAYER_SAYING, game.getCurrentAndIterate().getName()));
        return helloMessages;
    }
}
