package com.example.mafia.gaming.executing;

import com.example.mafia.dto.Command;
import com.example.mafia.dto.ReplyText;
import com.example.mafia.gaming.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class KillExecutor {

    public static List<GameMessage> killAction(Game game, String target, String initiator) {
        Player targetPlayer = game.getPlayerWithId(target);
        if(targetPlayer.isAlive()) {
            log.info("Убивается игрок [{}] из игры [{}]", target, game.getLinkedChat());
            targetPlayer.kill();
            List<GameMessage> killMessages = new ArrayList<>();
            killMessages.add(new GameMessage(initiator, ReplyText.SHOOT));
            killMessages.add(new GameMessage(target, ReplyText.YOU_KILLED));
            killMessages.add(new GameMessage(game.getLinkedChat(), ReplyText.PLAYER_KILLED, targetPlayer.getName()));
            WinLostChecker.Winner winner = WinLostChecker.winnerExist(game.getPlayerList());
            if (winner != null) {
                game.setStatus(GameStatus.FINISHED);
                if (winner.equals(WinLostChecker.Winner.MAFIA)) {
                    killMessages.add(new GameMessage(game.getLinkedChat(), ReplyText.MAFIA_WON));
                } else {
                    killMessages.add(new GameMessage(game.getLinkedChat(), ReplyText.CITIZEN_WON));
                }

                GameMessage newGame = new GameMessage(game.getLinkedChat(), ReplyText.NEW_GAME_WANT);
                newGame.setAnswerVariantMap(Map.of(0, List.of(new AnswerVariant(
                        Map.of(AnswerKey.COMMAND, Command.OPEN_GAME.getCommand()),
                        "Открыть новую игру"
                ))));

                killMessages.add(newGame);
            }
            return killMessages;
        } else {
            log.info("Была получена цель убить игрока [{}], который уже мертв", target);
            return List.of(new GameMessage(initiator, ReplyText.CITIZEN_ALREADY_DEAD));
        }
    }
}
