package com.example.mafia.gaming;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Player creator;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Player> playerList;
    private GameStatus status;
    private DayState dayState;
    private String linkedChat;
    private int currentSpeaker;
    private boolean discussInProcess;

    public Game(@Nonnull Player creator, @Nonnull String linkedChat) {
        this.creator = creator;
        this.linkedChat = linkedChat;
        this.status = GameStatus.OPEN;

        this.playerList = new LinkedList<>();
        playerList.add(creator);
    }

    public Player getPlayerWithId(String userId) {
        for (Player player : playerList) {
            if (player.getUserId().equals(userId)) {
                return player;
            }
        }
        return null;
    }

    public Player getCurrentAndIterate() {
        int nextCandidate = currentSpeaker;
        Player currentAlivePlayer = null;
        while (nextCandidate < playerList.size() && currentAlivePlayer == null) {
            Player player = playerList.get(nextCandidate++);
            if (player.isAlive()) {
                currentSpeaker = nextCandidate;
                currentAlivePlayer = player;
            }
        }

        if (nextCandidate >= playerList.size()) {
            discussInProcess = false;
        }
        return currentAlivePlayer;
    }

    public Player getNextSayerCandidate() {
        int nextCandidate = currentSpeaker;
        while (nextCandidate < playerList.size()) {
            Player player = playerList.get(nextCandidate);
            if (player.isAlive()) {
                return player;
            }
        }
        return null;
    }

    public void startDiscuss() {
        this.discussInProcess = true;
        this.currentSpeaker = 0;
    }

    public List<Player> getAliveMafia() {
        return playerList.stream()
                .filter(player -> player.getRole().equals(Role.MAFIA) && player.isAlive())
                .collect(Collectors.toList());
    }

    public List<Player> getAliveCitizen() {
        return playerList.stream()
                .filter(player -> player.getRole().equals(Role.CITIZEN) && player.isAlive())
                .collect(Collectors.toList());
    }

    public List<Player> getAlivePlayers() {
        return playerList.stream()
                .filter(Player::isAlive)
                .collect(Collectors.toList());
    }
}

