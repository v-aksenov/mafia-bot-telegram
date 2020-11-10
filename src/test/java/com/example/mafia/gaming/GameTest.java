package com.example.mafia.gaming;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {

    private final List<Player> playerList = List.of(
            player("1"),
            player("2")
    );

    @Test()
    @DisplayName("итерация без ошибок. Конец круга определяется")
    public void sizeTest() {
        Game game = new Game(playerList.get(0), "123");
        game.getPlayerList().add(playerList.get(1));
        game.startDiscuss();
        assertTrue(game.isDiscussInProcess());
        assertEquals(game.getCurrentAndIterate().getUserId(), "1");
        assertTrue(game.isDiscussInProcess());
        assertEquals(game.getCurrentAndIterate().getUserId(), "2");
        assertFalse(game.isDiscussInProcess());
    }

    private Player player(String userId) {
        Player player = new Player();
        player.setUserId(userId);
        player.setState(PlayerState.ALIVE);
        return player;
    }

}