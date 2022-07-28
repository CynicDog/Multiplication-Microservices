package com.example.gamification.game.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ScoreCard {

    public static final int DEFAULT_SCORE = 10;

    @Id @GeneratedValue
    private Long cardId;

    private Long userId;

    private Long attemptId;

    @EqualsAndHashCode.Exclude
    private long scoreTimestamp;

    private int score;
}
