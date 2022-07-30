package com.example.gamification.game.repository;

import com.example.gamification.game.domain.LeaderBoardRow;
import com.example.gamification.game.domain.ScoreCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends CrudRepository<ScoreCard, Long> {

    @Query("SELECT SUM(s.score) FROM ScoreCard s WHERE s.userId = :userId GROUP BY s.userId")
    Optional<Integer> getTotalScoreForUser(@Param("userId") Long userId);

    @Query("SELECT NEW com.example.gamification.game.domain.LeaderBoardRow(s.userId, SUM(s.score)) " +
            "FROM ScoreCard s GROUP BY s.userId ORDER BY SUM(s.score) DESC")
    List<LeaderBoardRow> findFirst10();

    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);
}

