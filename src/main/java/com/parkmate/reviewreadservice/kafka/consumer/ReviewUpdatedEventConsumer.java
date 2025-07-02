package com.parkmate.reviewreadservice.kafka.consumer;

import com.parkmate.reviewreadservice.kafka.event.ReviewUpdatedEvent;
import com.parkmate.reviewreadservice.reviewread.application.ReviewReadIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewUpdatedEventConsumer {

    private final ReviewReadIntegrationService reviewReadIntegrationService;

    @KafkaListener(
            topics = "review.review.updated",
            groupId = "review-read.review-updated",
            containerFactory = "reviewUpdatedEventKafkaListener"
    )
    public void consume(ReviewUpdatedEvent event) {
        log.info("[Kafka] Received ReviewUpdatedEvent: {}", event);

        try {
            reviewReadIntegrationService.updateReview(
                    event.getReviewUuid(),
                    event.getContent(),
                    event.getRating(),
                    event.getImageUrls(),
                    event.getUpdatedAt()
            );
        } catch (Exception e) {
            log.error("[Kafka] Failed to update review document: reviewUuid={}, cause={}",
                    event.getReviewUuid(), e.getMessage(), e);
        }
    }
}