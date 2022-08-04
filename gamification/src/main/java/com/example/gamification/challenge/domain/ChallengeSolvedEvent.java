package com.example.gamification.challenge.domain;

import lombok.Value;

@Value
public class ChallengeSolvedEvent {
    long attemptId;
    boolean correct;
    int factorA;
    int factorB;

    long userId;
    String userAlias;
}
