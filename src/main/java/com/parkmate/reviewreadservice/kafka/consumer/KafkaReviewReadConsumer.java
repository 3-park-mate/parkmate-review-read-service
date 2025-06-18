package com.parkmate.reviewreadservice.kafka.consumer;

import com.parkmate.reviewreadservice.kafka.event.CreateReviewEvent;
import com.parkmate.reviewreadservice.kafka.event.CreateReviewJoinUserEvent;
import com.parkmate.reviewreadservice.reviewread.application.ReviewReadIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaReviewReadConsumer {

    private final ReviewReadIntegrationService reviewReadIntegrationService;

    private final ConcurrentHashMap<String, CompletableFuture<CreateReviewEvent>> reviewFutureMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, CompletableFuture<CreateReviewJoinUserEvent>> userFutureMap = new ConcurrentHashMap<>();

    @KafkaListener(topics = "create-review", groupId = "review-read-group", containerFactory = "createReviewEventKafkaListener")
    public void listenCreateReview(CreateReviewEvent event) {
        String userUuid = event.getUserUuid();
        log.info("[Kafka] Received CreateReviewEvent: {}", event);

        completeFuture(reviewFutureMap, userUuid, event);
        attemptJoin(userUuid);
    }

    @KafkaListener(topics = "create-review-join-user", groupId = "review-read-group", containerFactory = "createReviewJoinUserEventKafkaListener")
    public void listenCreateReviewJoinUser(CreateReviewJoinUserEvent event) {
        String userUuid = event.getUserUuid();
        log.info("[Kafka] Received CreateReviewJoinUserEvent: {}", event);

        completeFuture(userFutureMap, userUuid, event);
        attemptJoin(userUuid);
    }

    private <T> void completeFuture(ConcurrentHashMap<String, CompletableFuture<T>> map, String key, T value) {
        map.computeIfAbsent(key, k -> new CompletableFuture<>()).complete(value);
    }

    private void attemptJoin(String userUuid) {
        CompletableFuture<CreateReviewEvent> reviewFuture = reviewFutureMap.get(userUuid);
        CompletableFuture<CreateReviewJoinUserEvent> userFuture = userFutureMap.get(userUuid);

        if (reviewFuture != null && userFuture != null) {

            reviewFuture.thenCombine(userFuture, (review, user) -> {
                reviewReadIntegrationService.createReviewReadDocument(review, user);
                reviewFutureMap.remove(userUuid);
                userFutureMap.remove(userUuid);
                return null;
            });
        }
    }
}
