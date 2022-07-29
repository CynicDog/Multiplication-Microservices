package com.example.multiplication_service.challenge.controller;

import com.example.multiplication_service.challenge.domain.Challenge;
import com.example.multiplication_service.challenge.service.ChallengeGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/challenges")
class ChallengeController {

    private final ChallengeGeneratorService challengeGeneratorService;

    @GetMapping("/random")
    Challenge getRandomChallenge() {
        Challenge challenge = challengeGeneratorService.randomChallenge();

        log.info("A new challenge generated: {}", challenge);

        return challenge;
    }
}
