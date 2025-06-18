package com.parkmate.reviewreadservice.kafka.consumer;

import com.parkmate.reviewreadservice.kafka.event.UpdateUserProfileEvent;
import com.parkmate.reviewreadservice.reviewread.application.ReviewReadIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateUserProfileConsumer {

    private final ReviewReadIntegrationService reviewReadIntegrationService;

    @KafkaListener(
            topics = "update-user-profile",
            groupId = "review-read-group",
            containerFactory = "updateUserProfileEventKafkaListener"
    )
    public void listenUpdateUserProfile(UpdateUserProfileEvent event) {
        log.info("[Kafka] Received UpdateUserProfileEvent: {}", event);

        reviewReadIntegrationService.updateUserNameInReviews(
                event.getUserUuid(),
                event.getName()
        );
    }
}
