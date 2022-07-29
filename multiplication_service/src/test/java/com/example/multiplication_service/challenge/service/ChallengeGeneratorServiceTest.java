package com.example.multiplication_service.challenge.service;


import com.example.multiplication_service.challenge.domain.Challenge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
public class ChallengeGeneratorServiceTest {

    private ChallengeGeneratorService challengeGeneratorService;

    @Spy
    private Random random;

    @BeforeEach
    public void setUp() {
        challengeGeneratorService = new ChallengeGeneratorServiceImpl(random);
    }

    @Test
    public void generateRandomFactorsWithinRange() {
        // given
        given(random.nextInt(89)).willReturn(20, 30);

        // when
        Challenge challenge = challengeGeneratorService.randomChallenge();

        // then
        then(challenge).isEqualTo(new Challenge(31, 41));
    }
}
