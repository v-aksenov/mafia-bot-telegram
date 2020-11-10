package com.example.mafia.gaming.executing;

import com.example.mafia.gaming.Game;
import com.example.mafia.gaming.GameMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ActionExecutor {

    public List<GameMessage> executeActions(Game game, List<Action> actionList) {
        List<GameMessage> allActionsMessageList = new ArrayList<>();
        for (Action a : actionList) {
            allActionsMessageList.addAll(execute(game, a));
        }
        return allActionsMessageList;
    }

    public List<GameMessage> execute(Game game, Action action) {
        log.info("Выполняю action [{}]", action.getActionType().name());
        switch (action.getActionType()) {
            case KILL:
                return KillExecutor.killAction(game, action.getTarget(), action.getInitiator());
            case LYNCH:
                return LynchExecutor.lynchAction(game, action.getTarget());
            case SAY_HELLO:
                return SayHelloExecutor.sayHelloAction(game);
            case CHECK_SUSPECT:
                return CheckSuspectExecutor.checkSuspect(game, action.getTarget());
            case SUGGEST_LYNCH:
                return SuggestLynchExecutor.suggestLynch(game, action.getTarget());
            case TO_DAY:
                return ToDayExecutor.toDay(game);
            case TO_NIGHT:
                return ToNightExecutor.toNight(game);
            case NEXT_SAYING:
                return NextSayingExecutor.nextSaying(game);
            case CHANGE_DAY_STATE:
                return ChangeGameStateExecutor.changeGameState(game);
            default:
                throw new IllegalStateException();
        }
    }
}
