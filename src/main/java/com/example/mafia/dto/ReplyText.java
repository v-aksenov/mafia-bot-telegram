package com.example.mafia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ReplyText {
    OPEN_GAME_EXIST("есть открытая или начатая игра"),
    GAME_OPEN_SUCCESS("открыта новая игра. Номер игры [%s], по нему к вам могут присоединиться друзья"),
    FINISHING_BY_NOT_CREATOR("Игру пытается закончить не создатель"),
    FINISHED_BY_COMMAND("Игра завершена успешно"),
    GAME_NOT_FOUND("Не найдена запущенная игра в чате"),
    NO_GAME_TO_START("Нет игры для начала"),
    STARTING_NOT_BY_CREATOR("игру пытается стартануть не создатель"),
    GAME_START_SUCCESS("Игра началась"),
    INVITE_ERROR("Не удалось добавить игрока"),
    INVITE_SUCCESS("Игрок добавлен"),
    DAY_STARTED("Ночь закончилась. Начинается день"),
    FIRST_DAY_STARTED("Начинается первый день"),
    NIGHT_STARTED("Город засыпает. Начинается ночь..."),
    GAME_OPENED("Новая игра создана"),
    GAME_FINISHED("Игра завершена"),
    GAME_ENDED("Игра закончена игровым способом"),
    YOU_KILLED("Вы были убиты"),
    YOU_SURVIVED("Вы пережили ночь"),
    PLAYER_KILLED("Житель %s убит"),
    YOU_MAFIA("Ты мафия"),
    YOU_CITIZEN("Ты мирный житель"),
    CITIZEN_LYNCHED("Убит мирный житель"),
    LYNCHED_MAFIA("Вы линчевали мафию"),
    LYNCHED_CITIZEN("Вы линчевали жителя"),
    YOU_LYNCHED("Вас линчевали"),
    SHOOT("Бам! Вы выстрелили"),
    MAFIA_WON("Мафия победила!"),
    CITIZEN_WON("Жители победили!"),
    LYNCH_SUGGESTION("На линчевание выставлен житель %s. Голосуем"),
    PLAYER_SAYING("Слово за игроком: %s"),
    LAST_SAID("Последний высказался"),
    MAFIA_CHOOSE_TARGET("Время убивать! Выбери цель!"),
    CITIZEN_ALREADY_DEAD("Житель уже мертв. Выбери другую цель"),
    KILL_CANDIDATE("Кандидат на убийство: %s"),
    BOT_WAS_INVITED("Всем привет! Я бот для игры в мафию. Умею много, но не все :)"),
    NO_INVITE_TARGET("Добавь игрока через ответ на сообщение игрока + твой текст /invite"),
    START_PRIVATE("Привет! Я бот для игры в мафию. Чтобы начать игру, добавь меня в чат."),
    YOU_SAYING("Твоя очередь говорить. Нажми кнопку чтобы передать слово следующему игроку"),
    WANNA_JOIN("Для добавления к игре набери " + Command.JOIN.getCommand() + " + номер игры хоста. \n Пример " + Command.JOIN.getCommand() + " 13"),
    NEW_GAME_WANT("Для начала новой игры нажмите кнопку"),
    WANNA_BE_INVITED("Хост игры получил уведомление о том, что вы хотите вступить. Подождите его решения"),
    HOST_NOT_FOUND("Игра с номером %s не найдена"),
    HOST_WANNA_INVITE_NOTIFICATION("Игрок %s хочет вступить в вашу игру. Принять?"),
    ;

    public static List<ReplyText> REQUIRED_REPLY = List.of(
            BOT_WAS_INVITED,
            GAME_OPENED,
            GAME_ENDED,
            GAME_FINISHED,
            GAME_START_SUCCESS
    );

    String text;
}
