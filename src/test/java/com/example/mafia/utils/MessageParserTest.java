package com.example.mafia.utils;

import com.example.mafia.dto.Command;
import com.example.mafia.dto.MessageType;
import com.example.mafia.dto.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MessageParserTest {

    //todo нагенерить нормальных update
//
//    private final MessageParser messageParser = new MessageParser("mafia_principal_bot");
//
//    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//    @Test
//    @DisplayName("отправили обычный текст в личку")
//    public void parsePrivateMessage() throws IOException {
//        Message parse = messageParser.parse(toUpdate(loadResource("just text")));
//        Assertions.assertEquals(parse.getMessageType(), MessageType.PRIVATE_TEXT);
//        fillingAsserts(parse);
//    }
//
//    @Test
//    @DisplayName("отправили команду в личку")
//    public void parsePrivateCommand() throws IOException {
//        Message parse = messageParser.parse(toUpdate(loadResource("command")));
//        Assertions.assertEquals(parse.getMessageType(), MessageType.PRIVATE_COMMAND);
//        fillingAsserts(parse);
//    }
//
//    @Test
//    @DisplayName("отправили команду /open_game в чат")
//    public void parseChatCommand() throws IOException {
//        Message parse = messageParser.parse(toUpdate(loadResource("command to chat with bot")));
//        Assertions.assertEquals(parse.getMessageType(), MessageType.CHAT_COMMAND);
//        assertEquals(parse.getCommand(), Command.OPEN_GAME);
//        fillingAsserts(parse);
//    }
//
//    @Test
//    @DisplayName("создали чат с ботом.")
//    public void parseChatCreate() throws IOException {
//        Message parse = messageParser.parse(toUpdate(loadResource("create chat")));
//        Assertions.assertEquals(parse.getMessageType(), MessageType.CHAT_CREATE);
//        fillingAsserts(parse);
//    }
//
//    @Test
//    @DisplayName("пригласили бота в чат")
//    public void parseChatInvite() throws IOException {
//        Message parse = messageParser.parse(toUpdate(loadResource("invite to chat")));
//        Assertions.assertEquals(parse.getMessageType(), MessageType.CHAT_INVITE);
//        fillingAsserts(parse);
//    }
//
//    @Test
//    @DisplayName("написали /invite @имя чувачка")
//    public void parseChatInvite1() throws IOException {
//        Message parse = messageParser.parse(toUpdate(loadResource("invite in one msg to chat")));
//        Assertions.assertEquals(parse.getMessageType(), MessageType.CHAT_COMMAND);
//        assertEquals(parse.getCommand(), Command.INVITE);
//        fillingAsserts(parse);
//    }
//
//    @Test
//    @DisplayName("прислали стикер в личку")
//    public void parseSticker() throws IOException {
//        Message parse = messageParser.parse(toUpdate(loadResource("animatedSticker")));
//        Assertions.assertEquals(parse.getMessageType(), MessageType.PRIVATE_TEXT);
//        fillingAsserts(parse);
//    }
//
//    @Test
//    @DisplayName("в личку написал человек без username")
//    public void parseNoUsername() throws IOException {
//        Message parse = messageParser.parse(toUpdate(loadResource("bot text player no@")));
//        Assertions.assertEquals(parse.getMessageType(), MessageType.PRIVATE_TEXT);
//        fillingAsserts(parse);
//    }
//
//    private Update toUpdate(String updateString) throws JsonProcessingException {
//        return objectMapper.readValue(updateString, Update.class);
//    }
//
//    private String loadResource(String name) throws IOException {
//        File file = ResourceUtils.getFile("classpath:updates/" + name);
//        return new String(Files.readAllBytes(Path.of(file.toURI())));
//    }
//
//    private void fillingAsserts(Message message) {
//        assertNotNull(message.getLastName());
//        assertNotNull(message.getFirstName());
//        assertNotNull(message.getUserId());
//        assertNotNull(message.getChatId());
//    }
}