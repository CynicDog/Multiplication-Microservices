package com.example.gamification.game.controller;

import com.example.gamification.challenge.ChallengeSolvedDTO;
import com.example.gamification.game.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attempts")
public class GameController {

    private final GameService gameService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    void postResult(@RequestBody ChallengeSolvedDTO solvedDTO) {
        gameService.newAttemptForUser(solvedDTO);
    }
}
