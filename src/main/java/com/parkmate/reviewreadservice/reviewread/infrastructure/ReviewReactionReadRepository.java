package com.parkmate.reviewreadservice.reviewread.infrastructure;

import com.parkmate.reviewreadservice.reviewread.domain.ReviewReactionRead;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReviewReactionReadRepository extends MongoRepository<ReviewReactionRead, String> {

    Optional<ReviewReactionRead> findByReviewUuidAndUserUuid(String reviewUuid, String userUuid);
}
