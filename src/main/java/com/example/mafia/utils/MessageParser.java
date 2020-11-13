package com.example.mafia.utils;

import com.example.mafia.dto.Command;
import com.example.mafia.dto.Message;
import com.example.mafia.dto.MessageType;
import com.example.mafia.dto.ReplyToMessage;
import com.example.mafia.gaming.AnswerKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class MessageParser {

    private final String thisBotUsername;

    public MessageParser(String thisBotUsername) {
        this.thisBotUsername = thisBotUsername;
    }

    public Message parse(Update update) {
        if (update.getCallbackQuery() != null) {
            return parseLikeCallback(update.getCallbackQuery());
        } else {
            return parseLikeMessage(update);
        }
    }

    private Message parseLikeMessage(Update update) {
        Message message = new Message();
        message.setText(update.getMessage().getText());
        message.setChat(update.getMessage().getChat().isGroupChat());

        if (update.getMessage().isCommand()) {
            message.setCommand(extractCommand(update.getMessage().getText()));
        }

        if (update.getMessage().getReplyToMessage() != null) {
            message.setReplyToMessage(new ReplyToMessage(
                    update.getMessage().getReplyToMessage().getFrom().getId().toString(),
                    update.getMessage().getReplyToMessage().getChat().getId().toString()
            ));
        }

        MessageType messageType = analyseMessageType(update);
        message.setMessageType(messageType);

        message.setChatId(update.getMessage().getChat().getId().toString());
        message.setFirstName(update.getMessage().getFrom().getFirstName());
        message.setLastName(update.getMessage().getFrom().getLastName());
        message.setUserId(update.getMessage().getFrom().getId().toString());

        return message;
    }

    private Message parseLikeCallback(CallbackQuery callbackQuery) {
        Message message = new Message();
        Map<AnswerKey, String> decoded = CallbackEncoder.decode(callbackQuery.getData());
        Command command = extractCommand(Objects.requireNonNull(decoded.get(AnswerKey.COMMAND)));
        message.setCommand(command);
        String chatId = decoded.get(AnswerKey.RELATED_GAME);
        String targetId = decoded.get(AnswerKey.TARGET);
        message.setUserId(callbackQuery.getFrom().getId().toString());
        message.setReplyToMessage(new ReplyToMessage(targetId, chatId));
        message.setFirstName(callbackQuery.getFrom().getFirstName());
        message.setLastName(callbackQuery.getFrom().getLastName());
        //  Коллбеки будут всегда в личных сообщениях. Пока
        message.setMessageType(MessageType.PRIVATE_COMMAND);
        return message;
    }

    private Command extractCommand(String text) {
        if (StringUtils.containsWhitespace(text)) {
            String[] s = StringUtils.split(text, " ");
            if (s == null || s.length == 0) {
                log.info("Текст [{}] не содержит команды. Хотя должен бы.", text);
                throw new IllegalStateException("При парсинге update произошел конфьюз");
            } else {
                return Command.withCommand(s[0]);
            }
        }

        log.info("Пришла простая команда [{}]", text);
        return Command.withCommand(text);
    }

    private MessageType analyseMessageType(Update update) {
        boolean command = update.getMessage().isCommand();
        Boolean groupChat = update.getMessage().getChat().isGroupChat();

        if (isThisBotInvited(update)) {
            return MessageType.CHAT_INVITE;
        }

        if(Boolean.TRUE.equals(update.getMessage().getGroupchatCreated())) {
            return MessageType.CHAT_CREATE;
        }

        if(command && groupChat) {
            return MessageType.CHAT_COMMAND;
        }

        if(command) {
            return MessageType.PRIVATE_COMMAND;
        }

        if(groupChat) {
            return MessageType.CHAT_TEXT;
        }

        return MessageType.PRIVATE_TEXT;
    }

    private boolean isThisBotInvited(Update update) {
        if(!CollectionUtils.isEmpty(update.getMessage().getNewChatMembers())) {
            List<org.telegram.telegrambots.meta.api.objects.User> newChatMembers = update.getMessage().getNewChatMembers();
            for (org.telegram.telegrambots.meta.api.objects.User u : newChatMembers) {
                if(u.getBot() && u.getUserName().equals(thisBotUsername)) {
                    return true;
                }
            }
        }

        return false;
    }
}
