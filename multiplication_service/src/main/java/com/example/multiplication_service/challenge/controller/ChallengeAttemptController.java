package com.example.multiplication_service.challenge.controller;

import com.example.multiplication_service.challenge.domain.ChallengeAttempt;
import com.example.multiplication_service.challenge.domain.ChallengeAttemptDTO;
import com.example.multiplication_service.challenge.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/attempts")
class ChallengeAttemptController {

    private final ChallengeService challengeService;

    @PostMapping
    ResponseEntity<ChallengeAttempt> postResult(
            @RequestBody @Valid ChallengeAttemptDTO challengeAttemptDTO) {
        return ResponseEntity.ok(challengeService.verifyAttempt(challengeAttemptDTO));
    }

    @GetMapping
    ResponseEntity<List<ChallengeAttempt>> getStatistics(
            @RequestParam("alias") String alias) {
        return ResponseEntity.ok(challengeService.getStatsForUser(alias));
    }
}
