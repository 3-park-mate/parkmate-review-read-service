package com.parkmate.reviewreadservice.kafka.consumer;

import com.parkmate.reviewreadservice.kafka.event.ReviewReactionUpdatedEvent;
import com.parkmate.reviewreadservice.reviewread.application.ReviewReadIntegrationService;
import com.parkmate.reviewreadservice.reviewread.infrastructure.ReviewMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewReactionUpdatedConsumer {

    private final ReviewReadIntegrationService reviewReadIntegrationService;

    @KafkaListener(
            topics = "review.review-reactions.updated",
            groupId = "review-read.reaction-updated",
            containerFactory = "reviewReactionUpdatedEventKafkaListener"
    )
    public void listenReactionUpdated(ReviewReactionUpdatedEvent event) {
        log.info("[Kafka] Received ReviewReactionUpdatedEvent: {}", event);

        reviewReadIntegrationService.updateReaction(
                event.getReviewUuid(),
                event.getReactionType(),
                event.getPreviousReactionType(),
                event.getTimestamp()
        );
    }
}
