package com.example.multiplication_service.challenge.service;

import com.example.multiplication_service.challenge.domain.ChallengeAttempt;
import com.example.multiplication_service.challenge.domain.ChallengeSolvedEvent;
import com.example.multiplication_service.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
public class ChallengeEventPublisherTest {

    private ChallengeEventPublisher challengeEventPublisher;

    @Mock
    private AmqpTemplate amqpTemplate;

    @BeforeEach
    public void setUp() {
        challengeEventPublisher = new ChallengeEventPublisher(amqpTemplate, "test.topic");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void sendsAttempt(boolean correct) {
        // given
        ChallengeAttempt attempt = createTestAttempt(correct);

        // when
        challengeEventPublisher.challengeSolved((attempt));

        var exchangeCaptor = ArgumentCaptor.forClass(String.class);
        var routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        var eventCaptor = ArgumentCaptor.forClass(ChallengeSolvedEvent.class);

        // then
        verify(amqpTemplate).convertAndSend(exchangeCaptor.capture(), routingKeyCaptor.capture(), eventCaptor.capture());

        then(exchangeCaptor.getValue()).isEqualTo("test.topic");
        then(routingKeyCaptor.getValue()).isEqualTo("attempt." + (correct ? "correct": "wrong"));
        then(eventCaptor.getValue()).isEqualTo(solvedEvent(correct));
    }

    public ChallengeAttempt createTestAttempt(boolean correct) {
        return new ChallengeAttempt(1L, new User(10L, "john"), 30, 40, correct? 1200 : 1300, correct);
    }

    public ChallengeSolvedEvent solvedEvent(boolean correct) {
        return new ChallengeSolvedEvent(1L, correct, 30, 40, 10L, "john");
    }
}
