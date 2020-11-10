package com.example.mafia.handlers.command;

import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;

public interface CommandHandler {

    HandleResponse handleCommand(Message message);
}
