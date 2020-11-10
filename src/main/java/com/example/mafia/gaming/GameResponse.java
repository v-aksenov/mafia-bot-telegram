package com.example.mafia.gaming;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GameResponse {
    List<GameMessage> messageList;
}
