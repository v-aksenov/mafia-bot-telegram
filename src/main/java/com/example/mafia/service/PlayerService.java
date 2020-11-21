package com.example.mafia.service;

import com.example.mafia.gaming.Player;
import com.example.mafia.repository.PlayerRepository;
import com.example.mafia.utils.PlayerIdentifier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public void addPlayer(Player player) {
        log.info("Сохраняю нового игрока");
        playerRepository.save(player);
    }

    public Optional<Player> findPlayer(String userId) {
        return playerRepository.findByUserId(userId);
    }

    public List<Player> identPlayers(List<Player> playerList) {
        List<Player> players = new PlayerIdentifier().identPlayers(playerList);
        for (Player p : players) {
            playerRepository.save(p);
        }
        return players;
    }
}
