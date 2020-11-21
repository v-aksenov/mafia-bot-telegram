package com.example.mafia.utils;

import com.example.mafia.gaming.Player;
import com.example.mafia.gaming.PlayerState;
import com.example.mafia.gaming.Role;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
public class PlayerIdentifier {

    public List<Player> identPlayers(List<Player> playerList) {
        int mafiaIndex = chooseMafiaIndex(playerList.size());
        IntStream.range(0, playerList.size()).forEach(
                index -> {
                    if (index == mafiaIndex) {
                        identMafia(playerList.get(index));
                    } else {
                        identCitizen(playerList.get(index));
                    }
                }
        );

        return playerList;
    }

    private void identCitizen(Player citizen) {
        citizen.setRole(Role.CITIZEN);
        citizen.setState(PlayerState.ALIVE);
        log.info("Игрок [{}:{}] - мирный житель", citizen.getUserId(), citizen.getName());
    }

    private void identMafia(Player mafia) {
        mafia.setRole(Role.MAFIA);
        mafia.setState(PlayerState.ALIVE);
        log.info("Игрок [{}:{}] - мафия", mafia.getUserId(), mafia.getName());
    }

    private int chooseMafiaIndex(int playerListSize) {
        Random random = new Random();
        int mafiaInt = 0;
        if (playerListSize > 1) {
            mafiaInt = random.nextInt(playerListSize - 1);
            log.info("Мафия будет игрок под номером [{}]", mafiaInt);
        }
        return mafiaInt;
    }
}
