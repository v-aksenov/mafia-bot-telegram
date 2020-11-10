package com.example.mafia.utils;

import com.example.mafia.gaming.AnswerKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class CallbackEncoder {

    public static String encode(Map<AnswerKey, String> hiddenCommand) {
        StringBuilder callback = new StringBuilder();
        hiddenCommand.keySet().forEach(answerKey ->
                callback
                        .append(answerKey.name())
                        .append(":")
                        .append(hiddenCommand.get(answerKey))
                        .append(";"));
        log.info("Закодировал callback: [{}]", callback.toString());
        return callback.toString();
    }

    public static Map<AnswerKey, String> decode(String callback) {
        log.info("начинаю раскодировать callback: [{}]", callback);
        Map<AnswerKey, String> decoded = new HashMap<>();
        String[] split = Objects.requireNonNull(StringUtils.split(callback, ";"));
        log.info("Получено [{}] пар", split.length);
        Arrays.stream(split).forEach(
                pair -> decoded.putAll(decodePair(pair))
        );
        StringBuilder decodedKeys = new StringBuilder();
        decoded.keySet().forEach(key -> decodedKeys.append(key.name()).append(";"));
        log.info("После раскодирования присутствуют следующие сущности: [{}]", decodedKeys);
        return decoded;
    }

    private static Map<AnswerKey, String> decodePair(String pair) {
        log.info("Раскодирую пару [{}]", pair);
        String[] split = StringUtils.split(pair, ":");
        String key = Objects.requireNonNull(split)[0];
        String value = split[1];
        AnswerKey answerKey = AnswerKey.valueOf(key);
        return Map.of(answerKey, value);
    }
}
