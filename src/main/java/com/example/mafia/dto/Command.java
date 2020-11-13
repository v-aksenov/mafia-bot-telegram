package com.example.mafia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Command {
    START("/start"),
    OPEN_GAME("/opengame"),
    START_GAME("/startgame"),
    FINISH_GAME("/finishgame"),
    INVITE("/invite"),
    WANNA_INVITE("/wanna_invite"),
    KILL("/kill"),
    NEXT_PLAYER("/next_player"),
    ;
    private final String command;

    private final static List<Command> allCommands = Arrays.asList(values());

    public static Command withCommand(String command) {
        for (Command c : allCommands) {
            if(command.equals(c.getCommand())) {
                return c;
            }
        }

        return null;
    }

}
