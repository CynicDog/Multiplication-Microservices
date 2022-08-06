package com.example.gamification.game.service;

import com.example.gamification.game.domain.BadgeCard;
import com.example.gamification.game.domain.BadgeType;
import com.example.gamification.game.domain.LeaderBoardRow;
import com.example.gamification.game.repository.BadgeRepository;
import com.example.gamification.game.repository.ScoreRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
public class LeaderBoardServiceImplTest {

    private LeaderBoardServiceImpl leaderBoardService;

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private BadgeRepository badgeRepository;

    @BeforeEach void setUp() {
        leaderBoardService = new LeaderBoardServiceImpl(scoreRepository, badgeRepository);
    }

    @Test
    public void retrieveLeaderBoardTest() {
        // given
        LeaderBoardRow scoreRaw = new LeaderBoardRow(1L, 300L, List.of());

        given(scoreRepository.findFirst10())
                .willReturn(List.of(scoreRaw));
        given(badgeRepository.findByUserIdOrderByBadgeTimestampDesc(1L))
                .willReturn(List.of(new BadgeCard(1L, BadgeType.LUCKY_NUMBER)));

        // when
        List<LeaderBoardRow> leaderBoard = leaderBoardService.getCurrentLeaderBoard();

        // then
        List<LeaderBoardRow> expectedLeaderBoard = List.of(
                new LeaderBoardRow(1L, 300L, List.of(BadgeType.LUCKY_NUMBER.getDescription())));
        then(leaderBoard).isEqualTo(expectedLeaderBoard);
    }
}
