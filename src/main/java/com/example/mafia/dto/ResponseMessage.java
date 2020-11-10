package com.example.mafia.dto;

import lombok.Value;

import java.util.List;

@Value
public class ResponseMessage {
    String recipientChatId;
    String messageText;
    List<MessageButton> messageButtonList;
}