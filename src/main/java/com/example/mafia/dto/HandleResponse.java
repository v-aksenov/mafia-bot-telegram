package com.example.mafia.dto;

import com.example.mafia.gaming.GameResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HandleResponse {
    public static final HandleResponse OK = HandleResponse.builder().success(true).build();
    public static final HandleResponse ERROR = HandleResponse.builder().success(false).build();

    public static HandleResponse withError(String chatId) {
        return HandleResponse.builder()
                .success(false)
                .requestChatId(chatId)
                .build();
    }

    boolean success;
    String requestChatId;
    TechResponse techResponse;
    GameResponse gameResponse;
    String recipientChatId;

}