package com.example.mafia.gaming.executing;

import com.example.mafia.dto.Command;
import com.example.mafia.dto.ReplyText;
import com.example.mafia.gaming.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class NextSayingExecutor {

    public static List<GameMessage> nextSaying(Game game) {
        Player player = game.getCurrentAndIterate();
        if (player != null) {
            log.info("игра [{}]: слово за игроком [{}]", game.getLinkedChat(), player.getName());
            Player nextSayerCandidate = game.getNextSayerCandidate();
            List<GameMessage> gameMessageList = generateAnnounceForOtherAlive(game.getAlivePlayers(), player);
            gameMessageList.add(generateMessageForNextSaying(player, nextSayerCandidate));
            return gameMessageList;
        } else {
            log.info("игра [{}]: закончились кандидаты на разговорный круг", game.getLinkedChat());
            return List.of(new GameMessage(game.getLinkedChat(), ReplyText.LAST_SAID));
        }
    }

    private static List<GameMessage> generateAnnounceForOtherAlive(List<Player> playerList, Player except) {
        List<GameMessage> gameMessageList = new ArrayList<>();
        playerList.forEach(player -> {
            if (!player.getId().equals(except.getId())) {
                gameMessageList.add(
                        new GameMessage(player.getUserId(), ReplyText.PLAYER_SAYING, player.getName()));
            }
        });
        return gameMessageList;
    }

    private static GameMessage generateMessageForNextSaying(Player current, Player nextSpeaker) {
        GameMessage gameMessage = new GameMessage(current.getUserId(), ReplyText.PLAYER_SAYING, current.getName());
        if (nextSpeaker == null) {
            log.info("Текущий говорящий [{}] последний", current.getName());
            gameMessage.setAnswerVariantMap(Map.of(
                    0, List.of(
                            new AnswerVariant(
                                    Map.of(AnswerKey.COMMAND, Command.NEXT_PLAYER.getCommand()),
                                    ReplyText.FINISH_SPEAK_CIRCLE.getText()))
                    )
            );
        } else {
            log.info("Текущий говорящий [{}], следующий [{}]", current.getName(), nextSpeaker.getName());
            gameMessage.setAnswerVariantMap(
                    Map.of(0, List.of(
                            new AnswerVariant(Map.of(
                                    AnswerKey.COMMAND,
                                    Command.NEXT_PLAYER.getCommand()),
                                    String.format(ReplyText.GIVE_WORD_NEXT.getText(), nextSpeaker.getName())))
                    )
            );
        }

        return gameMessage;
    }
}
