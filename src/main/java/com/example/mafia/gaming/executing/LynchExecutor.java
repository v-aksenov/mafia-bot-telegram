package com.example.mafia.gaming.executing;

import com.example.mafia.dto.ReplyText;
import com.example.mafia.gaming.Game;
import com.example.mafia.gaming.GameMessage;
import com.example.mafia.gaming.GameStatus;
import com.example.mafia.gaming.Player;
import com.example.mafia.gaming.Role;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LynchExecutor {

    public static List<GameMessage> lynchAction(Game game, String target) {
        List<GameMessage> lynchMessages = new ArrayList<>();

        Player targetPlayer = game.getPlayerWithId(target);
        targetPlayer.kill();

        GameMessage killedStatus = new GameMessage(game.getLinkedChat(),
                targetPlayer.getRole().equals(Role.MAFIA) ?
                        ReplyText.LYNCHED_MAFIA :
                        ReplyText.LYNCHED_CITIZEN);
        GameMessage targetMessage = new GameMessage(target, ReplyText.YOU_LYNCHED);

        lynchMessages.add(killedStatus);
        lynchMessages.add(targetMessage);

        if(WinLostChecker.isAnyMafiaAlive(game.getPlayerList())) {
            if(WinLostChecker.citizensLost(game.getPlayerList())) {
                log.info("Мафия победила");
                lynchMessages.add(new GameMessage(game.getLinkedChat(), ReplyText.MAFIA_WON));
                game.setStatus(GameStatus.FINISHED);
            }
        } else {
            log.info("Жители победили");
            lynchMessages.add(new GameMessage(game.getLinkedChat(), ReplyText.CITIZEN_WON));
            game.setStatus(GameStatus.FINISHED);
        }

        return lynchMessages;
    }
}
