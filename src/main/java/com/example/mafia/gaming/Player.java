package com.example.mafia.gaming;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String userId;
    private PlayerState state;
    private Role role;

    public void kill() {
        if(state.equals(PlayerState.ALIVE)) {
            state = PlayerState.DEAD;
        } else {
            throw new IllegalStateException("Игрок " + name + " уже мертв");
        }
    }

    public boolean isAlive() {
        return PlayerState.ALIVE.equals(state);
    }
}
