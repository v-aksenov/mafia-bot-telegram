package com.example.mafia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class MafiaApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(MafiaApplication.class, args);
	}

}
