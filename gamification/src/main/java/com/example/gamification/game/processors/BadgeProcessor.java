package com.example.gamification.game.processors;

import com.example.gamification.challenge.ChallengeSolvedDTO;
import com.example.gamification.game.domain.BadgeType;
import com.example.gamification.game.domain.ScoreCard;

import java.util.List;
import java.util.Optional;

public interface BadgeProcessor {
    Optional<BadgeType> processForOptionalBadge(
            int currentScore,
            List<ScoreCard> scoreCardList,
            ChallengeSolvedDTO solved
            );

    BadgeType badgeType();
}
