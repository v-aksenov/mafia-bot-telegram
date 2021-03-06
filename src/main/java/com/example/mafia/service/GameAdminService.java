package com.example.mafia.service;

import com.example.mafia.dto.ReplyText;
import com.example.mafia.dto.TechResponse;
import com.example.mafia.gaming.DayState;
import com.example.mafia.gaming.Game;
import com.example.mafia.gaming.GameStatus;
import com.example.mafia.gaming.Player;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class GameAdminService {

    private final GameDaService gameDaService;
    private final PlayerService playerService;

    private final Integer minPlayerAmount = 2;

    public TechResponse openGame(String linkedChat, Player firstPlayer) {
        Optional<Game> openGame = gameDaService.findOpenGame(linkedChat);
        Optional<Game> processingGame = gameDaService.findProcessingGame(linkedChat);
        if(openGame.isPresent() || processingGame.isPresent()) {
            log.info("В чате [{}] есть открытая или начатая игра", linkedChat);
            return new TechResponse(false, ReplyText.OPEN_GAME_EXIST);
        }

        Game game = new Game(firstPlayer, linkedChat);
        playerService.addPlayer(firstPlayer);
        Long gameId = gameDaService.saveGame(game);
        log.info("Для чата [{}] открыта новая игра, Id [{}]", linkedChat, gameId);
        return new TechResponse(true, ReplyText.GAME_OPEN_SUCCESS, gameId.toString());
    }

    public TechResponse startGame(String linkedChat, String starterId) {
        Optional<Game> gameToStart = gameDaService.findOpenGame(linkedChat);
        if (gameToStart.isEmpty()) {
            log.info("Получена команда начать игру из чата, в котором нет в статусе OPEN");
            return new TechResponse(false, ReplyText.NO_GAME_TO_START);
        }

        Game game = gameToStart.get();
        if (gameReadyToStart(game, starterId)) {
            return doStartGameActions(game);
        }

        log.info("Для чата [{}] не удалось стартануть игру", linkedChat);
        return new TechResponse(false, ReplyText.START_FAILED_MIN_PLAYERS, minPlayerAmount.toString());
    }

    private TechResponse doStartGameActions(Game game) {
        game.setStatus(GameStatus.IN_PROCESS);
        game.setDayState(DayState.DAY);
        gameDaService.saveGame(game);
        playerService.identPlayers(game.getPlayerList());
        log.info("В чате [{}] игра началась", game.getLinkedChat());
        return new TechResponse(true, ReplyText.GAME_START_SUCCESS);
    }

    private boolean gameReadyToStart(Game game, String starterId) {
        log.info("Получен запрос на старт игры [{}]. Количество участников [{}] из требуемых [{}]",
                game.getLinkedChat(), game.getPlayerList().size(), minPlayerAmount);
        return game.getCreator().getUserId().equals(starterId)
                && minPlayerAmount.compareTo(game.getPlayerList().size()) <= 0;
    }

    public TechResponse invitePlayer(String linkedChat, Player player) {
        log.info("Добавляю в игру в чате [{}] нового игрока [{}]", linkedChat, player.getUserId());
        try {
            playerService.addPlayer(player);
            Game game = gameDaService.findOpenGame(linkedChat).orElseThrow();
            game.getPlayerList().add(player);
            gameDaService.saveGame(game);
        } catch (NoSuchElementException e) {
            log.info("Не нашли чего-то: ");
            e.printStackTrace();
            return new TechResponse(false, ReplyText.INVITE_ERROR);
        }
        return new TechResponse(true, ReplyText.INVITE_SUCCESS);
    }

    public TechResponse inviteCandidate(String userId, Long gameId) {
        log.info("Игрок [{}] хочет вступить в игру [{}]", userId, gameId);
        Game openGameWithId = gameDaService.findOpenGameWithId(gameId);
        if (openGameWithId != null) {
            return new TechResponse(true, ReplyText.WANNA_BE_INVITED).withHostChatId(openGameWithId.getLinkedChat());
        } else
            return new TechResponse(false, ReplyText.HOST_NOT_FOUND, gameId.toString());
    }

    public TechResponse finishGame(String linkedChat, String userId) {
        log.info("Закончить игру в чате [{}] вызван игроком [{}]", linkedChat, userId);
        try {
            Game game = gameDaService.findOpenProcessingGame(linkedChat).orElseThrow();
            if (!userId.equals(game.getCreator().getUserId())) {
                log.info("Игру пытается закончить не создатель");
                return new TechResponse(false, ReplyText.FINISHING_BY_NOT_CREATOR);
            }

            game.setStatus(GameStatus.FINISHED);
            gameDaService.saveGame(game);
            log.info("Игра завершена успешно");
            return new TechResponse(true, ReplyText.FINISHED_BY_COMMAND);
        } catch (NoSuchElementException e) {
            log.info("Не найдена запущенная игра в чате");
            e.printStackTrace();
            return new TechResponse(false, ReplyText.GAME_NOT_FOUND);
        }
    }
}
