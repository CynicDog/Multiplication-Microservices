package com.example.gamification.game.service;

import com.example.gamification.challenge.ChallengeSolvedDTO;
import com.example.gamification.game.domain.BadgeType;
import lombok.Value;

import java.util.List;

public interface GameService {
    GameResult newAttemptForUser(ChallengeSolvedDTO challenge);

    @Value
    class GameResult {
        int score;
        List<BadgeType> badges;
    }
}
