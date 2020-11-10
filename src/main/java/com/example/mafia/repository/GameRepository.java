package com.example.mafia.repository;

import com.example.mafia.gaming.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findAllByLinkedChat(String linkedChat);

    Optional<Game> findTopByLinkedChatOrderByIdDesc(String linkedChat);
}
