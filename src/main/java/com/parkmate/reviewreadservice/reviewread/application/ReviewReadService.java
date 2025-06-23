package com.parkmate.reviewreadservice.reviewread.application;

import com.parkmate.reviewreadservice.kafka.event.ReactionType;
import com.parkmate.reviewreadservice.reviewread.dto.response.ReviewListResponseDto;

public interface ReviewReadService {

    ReviewListResponseDto getReviews(String parkingLotUuid, String cursor, int size);

    ReactionType getUserReactionType(String reviewUuid, String userUuid);
}
