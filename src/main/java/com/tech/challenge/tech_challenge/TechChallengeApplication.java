package com.tech.challenge.tech_challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TechChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechChallengeApplication.class, args);
	}

}
