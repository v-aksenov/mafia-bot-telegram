package com.example.mafia.gaming.executing;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Action {

    public static final Action HELLO_ACTION = Action.builder().actionType(ActionType.SAY_HELLO).build();
    public static final Action TO_DAY_ACTION = Action.builder().actionType(ActionType.TO_DAY).build();
    public static final Action TO_NIGHT_ACTION = Action.builder().actionType(ActionType.TO_NIGHT).build();
    public static final Action NEXT_PLAYER_SAYING = Action.builder().actionType(ActionType.NEXT_SAYING).build();
    public static final Action CHANGE_DAY_STATE = Action.builder().actionType(ActionType.CHANGE_DAY_STATE).build();

    private final ActionType actionType;
    String target;
    String initiator;
}
