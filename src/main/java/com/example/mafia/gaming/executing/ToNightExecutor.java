package com.example.mafia.gaming.executing;

import com.example.mafia.dto.Command;
import com.example.mafia.dto.ReplyText;
import com.example.mafia.gaming.AnswerKey;
import com.example.mafia.gaming.AnswerVariant;
import com.example.mafia.gaming.Game;
import com.example.mafia.gaming.GameMessage;
import com.example.mafia.gaming.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class ToNightExecutor {

    public static List<GameMessage> toNight(Game game) {
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

    private static List<AnswerVariant> getAnswerVariantsToKill(Game game) {
        return game.getAlivePlayers().stream()
                // Пока предлагаем убить каждого (даже себя)
//        return game.getAliveCitizen().stream()
                .map(citizen -> new AnswerVariant(
                        Map.of(
                                AnswerKey.COMMAND, Command.KILL.getCommand(),
                                AnswerKey.RELATED_GAME, game.getLinkedChat(),
                                AnswerKey.TARGET, citizen.getUserId()
                        ),
                        String.format(ReplyText.KILL_CANDIDATE.getText(), citizen.getName())))
                .collect(Collectors.toList());
    }
}