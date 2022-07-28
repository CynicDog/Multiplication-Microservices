package com.example.multiplication_service.challenge.service;

import com.example.multiplication_service.challenge.model.ChallengeAttempt;
import com.example.multiplication_service.challenge.model.ChallengeAttemptDTO;

import java.util.List;

public interface ChallengeService {
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt);

    List<ChallengeAttempt> getStatsForUser(final String userAlias);
}
