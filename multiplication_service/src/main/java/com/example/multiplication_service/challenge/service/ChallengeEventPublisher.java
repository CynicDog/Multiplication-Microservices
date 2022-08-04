package com.example.multiplication_service.challenge.service;

import com.example.multiplication_service.challenge.domain.ChallengeAttempt;
import com.example.multiplication_service.challenge.domain.ChallengeSolvedEvent;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChallengeEventPublisher {

    private final AmqpTemplate amqpTemplate;
    private final String challengesTopicExchange;

    public ChallengeEventPublisher(final AmqpTemplate amqpTemplate,
                                   @Value("${amqp.exchange.attempts}") final String challengesTopicExchange) {
        this.amqpTemplate = amqpTemplate;
        this.challengesTopicExchange = challengesTopicExchange;
    }

    public void challengeSolved(final ChallengeAttempt challengeAttempt) {
        ChallengeSolvedEvent event = buildEvent(challengeAttempt);
        String routingKey = "attempt." + (event.isCorrect() ? "correct" : "wrong");

        amqpTemplate.convertAndSend(challengesTopicExchange, routingKey, event);
    }

    public ChallengeSolvedEvent buildEvent(final ChallengeAttempt attempt) {
        return new ChallengeSolvedEvent(
                attempt.getId(),
                attempt.isCorrect(),
                attempt.getFactorA(),
                attempt.getFactorB(),
                attempt.getUser().getId(),
                attempt.getUser().getAlias());
    }
}
