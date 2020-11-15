package com.example.mafia.dto;

import lombok.Data;

@Data
public class Message {

    private MessageType messageType;
    private String text;
    private Command command;
    private boolean chat;
    private String chatId;
    private String userId;
    private String firstName;
    private String lastName;
    private ReplyToMessage replyToMessage;

    private String targetName;
    private String targetChatId;
}
