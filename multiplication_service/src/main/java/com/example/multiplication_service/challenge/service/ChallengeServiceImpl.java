package com.example.multiplication_service.challenge.service;

import com.example.multiplication_service.challenge.model.ChallengeAttempt;
import com.example.multiplication_service.challenge.model.ChallengeAttemptDTO;
import com.example.multiplication_service.challenge.repository.ChallengeAttemptRepository;
import com.example.multiplication_service.user.model.User;
import com.example.multiplication_service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final UserRepository userRepository;
    private final ChallengeAttemptRepository attemptRepository;

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO) {

        User user = userRepository.findByAlias(attemptDTO.getUserAlias()).orElseGet(() -> {
            log.info("Creating new user with alias {}", attemptDTO.getUserAlias());

            return userRepository.save(new User(attemptDTO.getUserAlias()));
        });

        boolean isCorrect = attemptDTO.getGuess() ==
                attemptDTO.getFactorA() * attemptDTO.getFactorB();

        ChallengeAttempt checkedAttempt = new ChallengeAttempt(
                null,
                user,
                attemptDTO.getFactorA(),
                attemptDTO.getFactorB(),
                attemptDTO.getGuess(),
                isCorrect
        );

        ChallengeAttempt storedAttempt = attemptRepository.save(checkedAttempt);

        return storedAttempt;
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(final String useAlias) {
        return attemptRepository.findTop10ByUserAliasOrderByIdDesc(useAlias);
    }
}
