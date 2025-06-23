package com.parkmate.reviewreadservice.reviewread.application;

import com.parkmate.reviewreadservice.kafka.event.ReactionType;
import com.parkmate.reviewreadservice.kafka.event.ReviewCreatedEvent;
import com.parkmate.reviewreadservice.kafka.event.CreateReviewJoinUserEvent;

import java.time.LocalDateTime;

public interface ReviewReadIntegrationService {

    void createReviewReadDocument(ReviewCreatedEvent reviewEvent, CreateReviewJoinUserEvent userEvent);

    void updateUserNameInReviews(String userUuid, String newName);

    void updateReaction(String reviewUuid, ReactionType newReaction, ReactionType previousReaction, LocalDateTime updatedAt);
}
