package com.example.gamification.game.service;

import com.example.gamification.challenge.domain.ChallengeSolvedEvent;
import com.example.gamification.game.domain.BadgeCard;
import com.example.gamification.game.domain.BadgeType;
import com.example.gamification.game.domain.ScoreCard;
import com.example.gamification.game.processors.BadgeProcessor;
import com.example.gamification.game.repository.BadgeRepository;
import com.example.gamification.game.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
class GameServiceImpl implements GameService {

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;
    private final List<BadgeProcessor> badgeProcessors;

    @Override
    @Transactional
    public GameResult newAttemptForUser(final ChallengeSolvedEvent challenge) {
        if (challenge.isCorrect()) {
            ScoreCard scoreCard = new ScoreCard(challenge.getUserId(), challenge.getAttemptId());
            scoreRepository.save(scoreCard);

            log.info("User {} scored {} points for attempt id {}", challenge.getUserAlias(), scoreCard.getScore(), challenge.getAttemptId());

            List<BadgeCard> badgeCards = processForBadges(challenge);

            return new GameResult(scoreCard.getScore(),
                    badgeCards
                            .stream()
                            .map(BadgeCard::getBadgeType)
                            .collect(Collectors.toList()));
        } else {
            log.info("Attempt id {} is not correct. " +
                            "User {} does not get score.",
                    challenge.getAttemptId(),
                    challenge.getUserAlias());

            return new GameResult(0, List.of());
        }
    }

    private List<BadgeCard> processForBadges(
            final ChallengeSolvedEvent solvedChallenge) {
        Optional<Integer> optTotalScore = scoreRepository.
                getTotalScoreForUser(solvedChallenge.getUserId());
        if (optTotalScore.isEmpty()) return Collections.emptyList();
        int totalScore = optTotalScore.get();

        List<ScoreCard> scoreCardList = scoreRepository
                .findByUserIdOrderByScoreTimestampDesc(solvedChallenge.getUserId());
        Set<BadgeType> alreadyGotBadges = badgeRepository
                .findByUserIdOrderByBadgeTimestampDesc(solvedChallenge.getUserId())
                .stream()
                .map(BadgeCard::getBadgeType)
                .collect(Collectors.toSet());

        List<BadgeCard> newBadgeCards = badgeProcessors.stream()
                .filter(bp -> !alreadyGotBadges.contains(bp.badgeType()))
                .map(bp -> bp.processForOptionalBadge(totalScore, scoreCardList, solvedChallenge))
                .flatMap(Optional::stream)
                .map(badgeType -> new BadgeCard(solvedChallenge.getUserId(), badgeType))
                .collect(Collectors.toList());

        badgeRepository.saveAll(newBadgeCards);

        return newBadgeCards;
    }

}