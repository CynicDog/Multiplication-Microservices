package com.example.multiplication_service.challenge.service;

import com.example.multiplication_service.challenge.domain.ChallengeAttempt;
import com.example.multiplication_service.challenge.domain.ChallengeSolvedDTO;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GamificationServiceClient {

    private final RestTemplate restTemplate;
    private final String gamificationHostUrl;

    public GamificationServiceClient(final RestTemplateBuilder builder, @Value("${service.gamification.host}") final String gamificationHostUrl) {
        restTemplate = builder.build();
        this.gamificationHostUrl = gamificationHostUrl;
    }

    public boolean sendAttempt(final ChallengeAttempt attempt) {

        try {
            ChallengeSolvedDTO solvedDTO = new ChallengeSolvedDTO(
                    attempt.getId(),
                    attempt.isCorrect(),
                    attempt.getFactorA(),
                    attempt.getFactorB(),
                    attempt.getUser().getId(),
                    attempt.getUser().getAlias());

            ResponseEntity<String> response = restTemplate.postForEntity(gamificationHostUrl + "/attempts", solvedDTO, String.class);

            log.info("Gamification service response: {}", response.getStatusCode());

            return response.getStatusCode().is2xxSuccessful();

        } catch (Exception error) {
            log.error("There was a problem sending the attempt.", error);

            return false;
        }
    }
}
