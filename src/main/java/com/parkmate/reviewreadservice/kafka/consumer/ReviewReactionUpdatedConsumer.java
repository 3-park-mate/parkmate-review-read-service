package com.parkmate.reviewreadservice.kafka.consumer;

import com.parkmate.reviewreadservice.kafka.event.ReviewReactionUpdatedEvent;
import com.parkmate.reviewreadservice.reviewread.infrastructure.ReviewMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewReactionUpdatedConsumer {

    private final ReviewMongoRepository reviewMongoRepository;

    @KafkaListener(
            topics = "review.review-reactions.updated",
            groupId = "review-read-group"
    )
    public void consume(ReviewReactionUpdatedEvent event) {
        log.info("[Kafka] Received ReviewReactionUpdatedEvent: {}", event);

        reviewMongoRepository.findByReviewUuid(event.getReviewUuid())
                .ifPresent(review -> {
                    review.updateReaction(
                            event.getReactionType(),
                            event.getPreviousReactionType(),
                            event.getTimestamp());
                    reviewMongoRepository.save(review);
                    log.info("[Mongo] 리액션 반영 완료 - reviewUuid: {}", event.getReviewUuid());
                });
    }
}
