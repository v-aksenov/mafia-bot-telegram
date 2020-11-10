package com.example.mafia.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MessageButton {
    String linkedMessage;
    String command;
    String text;
}
