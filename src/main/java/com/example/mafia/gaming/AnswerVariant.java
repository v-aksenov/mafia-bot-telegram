package com.example.mafia.gaming;

import lombok.Value;

import java.util.Map;

@Value
public class AnswerVariant {
    Map<AnswerKey, String> hiddenCommand;
    String frontText;
}
