package com.example.mafia.gaming.executing;

import com.example.mafia.dto.Command;
import com.example.mafia.dto.ReplyText;
import com.example.mafia.gaming.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class ToNightExecutor {

    public static List<GameMessage> toNight(Game game) {
        game.setDayState(DayState.NIGHT);
        List<GameMessage> gameMessages = List.of(new GameMessage(game.getLinkedChat(), ReplyText.NIGHT_STARTED));
        List<Player> aliveMafia = game.getAliveMafia();
        List<GameMessage> mafiaKillMessages = aliveMafia.stream()
                .map(player -> new GameMessage(player.getUserId(),
                        ReplyText.MAFIA_CHOOSE_TARGET,
                        getAnswerVariantsToKill(game)))
                .collect(Collectors.toList());
        log.info("Сообщений с предложением убийства для мафии - [{}]", mafiaKillMessages.size());
        return Stream.concat(gameMessages.stream(), mafiaKillMessages.stream()).collect(Collectors.toList());
    }

    private static Map<Integer, List<AnswerVariant>> getAnswerVariantsToKill(Game game) {
        Map<Integer, List<AnswerVariant>> answerMap = new HashMap<>();
        List<Player> alivePlayers = game.getAlivePlayers();
        IntStream.range(0, alivePlayers.size()).forEach(
                idx -> answerMap.put(idx,
                        List.of(new AnswerVariant(Map.of(
                                AnswerKey.COMMAND, Command.KILL.getCommand(),
                                AnswerKey.RELATED_GAME, game.getLinkedChat(),
                                AnswerKey.TARGET, alivePlayers.get(idx).getUserId()),
                                String.format(ReplyText.KILL_CANDIDATE.getText(), alivePlayers.get(idx).getName()))
                        )
                )
        );
        return answerMap;
    }
}
