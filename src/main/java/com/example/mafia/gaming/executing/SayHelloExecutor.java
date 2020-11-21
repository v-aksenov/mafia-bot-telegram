package com.example.mafia.gaming.executing;

import com.example.mafia.dto.Command;
import com.example.mafia.dto.ReplyText;
import com.example.mafia.gaming.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SayHelloExecutor {

    public static List<GameMessage> sayHelloAction(Game game) {
        List<GameMessage> helloMessages = new ArrayList<>();
        for (Player player : game.getPlayerList()) {
            helloMessages.add(new GameMessage(player.getUserId(), player.getRole().equals(Role.MAFIA) ?
                    ReplyText.YOU_MAFIA : ReplyText.YOU_CITIZEN));
        }
        helloMessages.add(new GameMessage(game.getLinkedChat(), ReplyText.FIRST_DAY_STARTED));

        game.startDiscuss();
        Player currentSpeaker = game.getCurrentAndIterate();
        helloMessages.add(new GameMessage(game.getLinkedChat(), ReplyText.PLAYER_SAYING, currentSpeaker.getName()));

        GameMessage youSayingMessage = new GameMessage(currentSpeaker.getUserId(), ReplyText.YOU_SAYING);
        youSayingMessage.setAnswerVariantMap(Map.of(0, List.of(new AnswerVariant(
                Map.of(AnswerKey.COMMAND, Command.NEXT_PLAYER.getCommand()),
                "Передать слово следующему"))));

        helloMessages.add(youSayingMessage);
        return helloMessages;
    }
}
