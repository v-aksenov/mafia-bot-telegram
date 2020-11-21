package com.example.mafia.service;

import com.example.mafia.gaming.Game;
import com.example.mafia.gaming.GameStatus;
import com.example.mafia.repository.GameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class GameDaService {
    private final GameRepository gameRepository;

    public Optional<Game> findOpenGame(String linkedChat) {
        return onlyOneGameInStatus(linkedChat, GameStatus.OPEN);
    }

    public Game findOpenGameWithId(Long gameId) {
        Optional<Game> one = gameRepository.findById(gameId);
        if (one.isPresent() && GameStatus.OPEN.equals(one.get().getStatus())) {
            log.info("Нашили OPEN игру с id [{}]", gameId);
            return one.get();
        }

        log.info("Не нашли OPEN игру с id [{}]", gameId);
        return null;
    }

    public Optional<Game> findProcessingGame(String linkedChat) {
        return onlyOneGameInStatus(linkedChat, GameStatus.IN_PROCESS);
    }

    public Optional<Game> findOpenProcessingGame(String linkedChat) {
        Optional<Game> processingGame = onlyOneGameInStatus(linkedChat, GameStatus.IN_PROCESS);
        if (processingGame.isPresent()) {
            log.info("Нашел игру IN_PROCESS для чата [{}]", linkedChat);
            return processingGame;
        } else {
            log.info("Нашел игру OPEN для чата [{}]", linkedChat);
            return onlyOneGameInStatus(linkedChat, GameStatus.OPEN);
        }
    }

    public Long saveGame(Game game) {
        Game save = gameRepository.save(game);
        return save.getId();
    }

    private Optional<Game> onlyOneGameInStatus(String linkedChat, GameStatus status) {
        List<Game> gameList = gameRepository.findAllByLinkedChat(linkedChat);
        log.info("В чате [{}] найдено [{}] игр.", linkedChat, gameList.size());
        List<Game> gamesInStatus = gameList
                .stream().filter(game -> status.equals(game.getStatus())).collect(Collectors.toList());

        if(gamesInStatus.isEmpty()) {
            log.info("Нет ни одной игре в статусе [{}]", status.name());
            return Optional.empty();
        }

        if(gamesInStatus.size() > 1) {
            log.info("Больше одной игры в статусе [{}]", status.name());
            throw new IllegalStateException("Больше одной открытой игры у одного чата");
        }

        log.info("Нашли одну игру в статусе [{}] с id[{}]", status.name(), gamesInStatus.get(0).getId());
        return Optional.of(gamesInStatus.get(0));
    }
}
