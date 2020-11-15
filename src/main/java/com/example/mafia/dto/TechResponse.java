package com.example.mafia.dto;

import lombok.Data;

@Data
public class TechResponse {
    private boolean success;
    private ReplyText replyText;
    private String bonusText;
    private String hostChatId;

    public TechResponse(boolean success, ReplyText replyText, String bonusText) {
        this.success = success;
        this.replyText = replyText;
        this.bonusText = bonusText;
        this.hostChatId = null;
    }

    public TechResponse(boolean success, ReplyText replyText) {
        this.success = success;
        this.replyText = replyText;
        this.bonusText = null;
        this.hostChatId = null;
    }

    public TechResponse withHostChatId(String hostChatId) {
        this.hostChatId = hostChatId;
        return this;
    }
}
