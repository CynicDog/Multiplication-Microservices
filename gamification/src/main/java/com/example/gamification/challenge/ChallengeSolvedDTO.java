package com.example.gamification.challenge;

import lombok.Value;

@Value
public class ChallengeSolvedDTO {
    long attemptId;
    boolean correct;
    int factorA;
    int factorB;

    long uesrId;
    String userAlias;
}
