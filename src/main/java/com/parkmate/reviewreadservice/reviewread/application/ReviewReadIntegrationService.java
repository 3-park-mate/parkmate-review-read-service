package com.parkmate.reviewreadservice.reviewread.application;

import com.parkmate.reviewreadservice.kafka.event.CreateReviewEvent;
import com.parkmate.reviewreadservice.kafka.event.CreateReviewJoinUserEvent;

public interface ReviewReadIntegrationService {

    void createReviewReadDocument(CreateReviewEvent reviewEvent, CreateReviewJoinUserEvent userEvent);

    void updateUserNameInReviews(String userUuid, String newName);
}
