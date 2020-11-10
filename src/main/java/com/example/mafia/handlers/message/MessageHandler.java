package com.example.mafia.handlers.message;

import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;

public interface MessageHandler {

    HandleResponse handleMessage(Message message);
}
