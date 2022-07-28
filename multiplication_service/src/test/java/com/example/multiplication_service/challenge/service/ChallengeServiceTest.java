package com.example.multiplication_service.challenge.service;

import com.example.multiplication_service.challenge.model.ChallengeAttempt;
import com.example.multiplication_service.challenge.model.ChallengeAttemptDTO;
import com.example.multiplication_service.challenge.repository.ChallengeAttemptRepository;
import com.example.multiplication_service.user.model.User;
import com.example.multiplication_service.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ChallengeServiceTest {

    private ChallengeService challengeService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChallengeAttemptRepository attemptRepository;

    @BeforeEach
    public void setUp() {
        challengeService = new ChallengeServiceImpl(userRepository, attemptRepository);
    }

    @Test
    public void checkCorrectAttemptTest() {
        // given
        given(attemptRepository.save(any())).will(returnsFirstArg());
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "john", 3000);

        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        // then
        then(resultAttempt.isCorrect()).isTrue();

        verify(userRepository).save(new User("john"));
        verify(attemptRepository).save(resultAttempt);
    }

    @Test
    public void checkIncorrectAttemptTest() {
        // given
        given(attemptRepository.save(any())).will(returnsFirstArg());
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "john", 3333);

        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        // then
        then(resultAttempt.isCorrect()).isFalse();

        verify(userRepository).save(new User("john"));
        verify(attemptRepository).save(resultAttempt);
    }

    @Test
    public void checkExistingUserTest() {
        //given
        given(attemptRepository.save(any())).will(returnsFirstArg());
        User existingUser = new User(1L, "john");

        given(userRepository.findByAlias("john")).willReturn(Optional.of(existingUser));
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "john", 5000);

        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        // then
        then(resultAttempt.isCorrect()).isFalse();
        then(resultAttempt.getUser()).isEqualTo(existingUser);

        verify(userRepository, never()).save(any());
        verify(attemptRepository).save(resultAttempt);
    }

    @Test
    public void retrieveStatsTest() {
        // given
        User user = new User("john");
        ChallengeAttempt attempt1 = new ChallengeAttempt(1L, user, 50, 60 , 3000, true);
        ChallengeAttempt attempt2 = new ChallengeAttempt(1L, user, 20, 40, 8000, false);

        List<ChallengeAttempt> lastAttempts = List.of(attempt1, attempt2);

        given(attemptRepository.findTop10ByUserAliasOrderByIdDesc("john")).willReturn(lastAttempts);

        // when
        List<ChallengeAttempt> latestAttemptsResult = challengeService.getStatsForUser("john");

        // then
        then(latestAttemptsResult).isEqualTo(lastAttempts);
    }
}
