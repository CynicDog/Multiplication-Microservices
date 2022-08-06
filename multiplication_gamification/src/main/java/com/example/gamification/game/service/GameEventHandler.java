package com.example.gamification.game.service;

import com.example.gamification.challenge.domain.ChallengeSolvedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameEventHandler {

    private final GameService gameService;

    @RabbitListener(queues = "${amqp.queue.gamification}")
    void handleMultiplicationSolved(final ChallengeSolvedEvent event) {
        log.info("ChallengeSolvedEvent received: {}", event.getAttemptId());

        try {
            gameService.newAttemptForUser(event);

        } catch (final Exception error){
            log.error("Error when trying to process ChallengeSolvedEvent", error);

            throw new AmqpRejectAndDontRequeueException(error);
        }
    }
}
