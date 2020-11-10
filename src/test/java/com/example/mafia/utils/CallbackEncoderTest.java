package com.example.mafia.utils;

import com.example.mafia.dto.Command;
import com.example.mafia.gaming.AnswerKey;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CallbackEncoderTest {

    Map<AnswerKey, String> answerMap = Map.of(
            AnswerKey.COMMAND, Command.KILL.getCommand(),
            AnswerKey.RELATED_GAME, "GAME",
            AnswerKey.TARGET, "TARGET"
    );

    String encoded = "COMMAND:/kill;RELATED_GAME:GAME;TARGET:TARGET;";

    @Test
    void encode() {
        String encode = CallbackEncoder.encode(answerMap);
        assertTrue(encode.contains("COMMAND:/kill;"));
        assertTrue(encode.contains("RELATED_GAME:GAME;"));
        assertTrue(encode.contains("TARGET:TARGET;"));
    }

    @Test
    void decode() {
        Map<AnswerKey, String> decode = CallbackEncoder.decode(encoded);
        assertEquals(decode.get(AnswerKey.TARGET), "TARGET");
        assertEquals(decode.get(AnswerKey.RELATED_GAME), "GAME");
        assertEquals(decode.get(AnswerKey.COMMAND), "/kill");
    }

    @Test
    void fullCircle() {
        String encode = CallbackEncoder.encode(answerMap);
        Map<AnswerKey, String> decode = CallbackEncoder.decode(encode);
        assertEquals(decode.get(AnswerKey.TARGET), "TARGET");
        assertEquals(decode.get(AnswerKey.RELATED_GAME), "GAME");
        assertEquals(decode.get(AnswerKey.COMMAND), "/kill");
    }
}