package com.example.gamification.game.processors;

import com.example.gamification.challenge.domain.ChallengeSolvedDTO;
import com.example.gamification.game.domain.BadgeType;
import com.example.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LuckyNumberBadgeProcessor implements BadgeProcessor {

    private static final int LUCKY_FACTOR = 8;

    @Override
    public Optional<BadgeType> processForOptionalBadge(
            int currentScore,
            List<ScoreCard> scoreCardList,
            ChallengeSolvedDTO solved) {
        return solved.getFactorA() == LUCKY_FACTOR || solved.getFactorB() == LUCKY_FACTOR ?
                Optional.of(BadgeType.LUCKY_NUMBER) :
                Optional.empty();
    }

    @Override
    public BadgeType badgeType() { return BadgeType.LUCKY_NUMBER; }
}
