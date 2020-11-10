package com.example.mafia.gaming.executing;

import com.example.mafia.gaming.Player;
import com.example.mafia.gaming.PlayerState;
import com.example.mafia.gaming.Role;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WinLostChecker {

    /**
     * Определяет победителя
     *
     * @param playerList
     * @return CITIZEN или MAFIA если кто-то выиграл
     * null, если победителя пока нет
     */
    public static Winner winnerExist(List<Player> playerList) {
        boolean citizensLost = citizensLost(playerList);
        boolean mafiaAlive = isAnyMafiaAlive(playerList);

        if (citizensLost) {
            return Winner.MAFIA;
        }

        if (!mafiaAlive) {
            return Winner.CITIZEN;
        }

        return null;
    }

    /**
     * У жителей есть шанс победить, если живых жителей осталось больше, чем мафии
     */
    public static boolean citizensLost(List<Player> playerList) {
        List<Player> mafiasAlive = new ArrayList<>();
        List<Player> citizensAlive = new ArrayList<>();

        for (Player p : playerList) {
            if (p.getState().equals(PlayerState.ALIVE)) {
                if(p.getRole().equals(Role.MAFIA)) {
                    mafiasAlive.add(p);
                } else {
                    citizensAlive.add(p);
                }
            }
        }
        log.info("Живых жителей [{}], живой мафии [{}]", citizensAlive.size(), mafiasAlive.size());
        return citizensAlive.size() < mafiasAlive.size();
    }

    /**
     *  true, если существует живая мафия
     */
    public static boolean isAnyMafiaAlive(List<Player> playerList) {
        for (Player p : playerList) {
            if (p.getRole().equals(Role.MAFIA) && p.getState().equals(PlayerState.ALIVE)) {
                log.info("Еще существует живая мафия");
                return true;
            }
        }
        log.info("Живой мафии не осталось");
        return false;
    }

    public enum Winner {
        MAFIA,
        CITIZEN
    }
}
