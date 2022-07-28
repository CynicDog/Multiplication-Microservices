package com.example.multiplication_service.challenge.service;

import com.example.multiplication_service.challenge.model.Challenge;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ChallengeGeneratorServiceImpl implements ChallengeGeneratorService {

    private final static int Min = 11;
    private final static int Max = 100;

    private final Random random;

    ChallengeGeneratorServiceImpl() { this.random = new Random(); }

    protected ChallengeGeneratorServiceImpl(final Random random) {
        this.random = random;
    }

    private int next() { return random.nextInt(Max - Min) + Min; }

    @Override
    public Challenge randomChallenge() {
        return new Challenge(next(), next());
    }
}
