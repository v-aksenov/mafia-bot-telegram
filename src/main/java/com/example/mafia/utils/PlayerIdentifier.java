package com.example.mafia.utils;

import com.example.mafia.gaming.Player;
import com.example.mafia.gaming.PlayerState;
import com.example.mafia.gaming.Role;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

@Slf4j
public class PlayerIdentifier {

    public List<Player> identPlayers(List<Player> playerList) {
        Random random = new Random();
        int mafiaInt = 0;
        if(playerList.size() > 1) {
            mafiaInt = random.nextInt(playerList.size()) - 1;
            log.info("Выбираем мафию рандомом");
        }
        for (int i = 0; i < playerList.size(); i++) {
            if(i == mafiaInt) {
                Player mafia = playerList.get(i);
                mafia.setRole(Role.MAFIA);
                mafia.setState(PlayerState.ALIVE);
                log.info("Игрок [{}:{}] - мафия", mafia.getId(), mafia.getName());
            } else {
                Player citizen = playerList.get(i);
                citizen.setRole(Role.CITIZEN);
                citizen.setState(PlayerState.ALIVE);
                log.info("Игрок [{}:{}] - мирный житель", citizen.getId(), citizen.getName());
            }
        }

        return playerList;
    }
}
