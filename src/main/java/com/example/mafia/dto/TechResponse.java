package com.example.mafia.dto;

import lombok.Value;

@Value
public class TechResponse {
    boolean success;
    ReplyText replyText;
    String bonusText;

    public TechResponse(boolean success, ReplyText replyText, String bonusText) {
        this.success = success;
        this.replyText = replyText;
        this.bonusText = bonusText;
    }

    public TechResponse(boolean success, ReplyText replyText) {
        this.success = success;
        this.replyText = replyText;
        this.bonusText = null;
    }
}
