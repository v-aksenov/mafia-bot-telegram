package com.example.mafia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyToMessage {
    String userId;
    String chatId;
}
