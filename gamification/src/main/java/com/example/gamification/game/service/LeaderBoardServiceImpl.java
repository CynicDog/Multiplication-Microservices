package com.example.gamification.game.service;

import com.example.gamification.game.domain.LeaderBoardRow;
import com.example.gamification.game.repository.BadgeRepository;
import com.example.gamification.game.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        List<LeaderBoardRow> scoreOnly = scoreRepository.findFirst10();

        return scoreOnly
                .stream()
                .map(row -> {
                    List<String> badges = badgeRepository.findByUserIdOrderByBadgeTimestampDesc(row.getUserId())
                            .stream()
                            .map(badge -> badge.getBadgeType().getDescription())
                            .collect(Collectors.toList());

                    return row.withBadges(badges);
                }).collect(Collectors.toList());
    }
}
