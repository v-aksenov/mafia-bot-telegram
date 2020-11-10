package com.example.mafia.handlers.message;

import com.example.mafia.dto.HandleResponse;
import com.example.mafia.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PrivateTextHandler implements MessageHandler {

    @Override
    public HandleResponse handleMessage(Message message) {
        log.info("PrivateTextHandler получил message");
        return HandleResponse.OK;
    }
}
