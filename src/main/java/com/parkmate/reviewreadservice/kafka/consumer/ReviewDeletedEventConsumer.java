package com.parkmate.reviewreadservice.kafka.consumer;

import com.parkmate.reviewreadservice.kafka.event.ReviewDeletedEvent;
import com.parkmate.reviewreadservice.reviewread.application.ReviewReadIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewDeletedEventConsumer {

    private final ReviewReadIntegrationService reviewReadIntegrationService;

    @KafkaListener(
            topics = "review.review.deleted",
            groupId = "review-read.review-deleted",
            containerFactory = "reviewDeletedEventKafkaListener"
    )
    public void listenReviewDeletedEvent(ReviewDeletedEvent event) {
        log.info("[Kafka] Received ReviewDeletedEvent: {}", event);

        reviewReadIntegrationService.softDeleteReview(
                event.getReviewUuid(),
                event.getDeletedAt()
        );
    }
}