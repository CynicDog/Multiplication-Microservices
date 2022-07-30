package com.example.gamification.game.service;

import com.example.gamification.game.domain.LeaderBoardRow;

import java.util.List;

public interface LeaderBoardService {

    List<LeaderBoardRow> getCurrentLeaderBoard();
}
