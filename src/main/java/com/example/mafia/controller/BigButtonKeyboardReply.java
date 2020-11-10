package com.example.mafia.controller;

import com.example.mafia.dto.Command;
import com.example.mafia.dto.ReplyText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class BigButtonKeyboardReply {

    public static ReplyKeyboard getKeyboardForText(ReplyText replyText) {
        //  Пока всегда только один набор. В будущем можно расширить
        return basicCommands();
    }

    private static ReplyKeyboard basicCommands() {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(rowWithCommand(Command.OPEN_GAME));
        keyboardRows.add(rowWithCommand(Command.START_GAME));
        keyboardRows.add(rowWithCommand(Command.FINISH_GAME));
        keyboardRows.add(rowWithCommand(Command.INVITE));

        return new ReplyKeyboardMarkup(keyboardRows);
    }

    private static KeyboardRow rowWithCommand(Command command) {
        KeyboardRow keyboardButtons = new KeyboardRow();
        keyboardButtons.add(command.getCommand());
        return keyboardButtons;
    }
}
