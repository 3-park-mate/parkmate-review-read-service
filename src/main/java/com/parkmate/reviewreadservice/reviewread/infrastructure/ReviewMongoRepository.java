package com.parkmate.reviewreadservice.reviewread.infrastructure;

import com.parkmate.reviewreadservice.reviewread.domain.ReviewRead;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewMongoRepository extends MongoRepository<ReviewRead, String> {

    List<ReviewRead> findByParkingLotUuidOrderByCreatedAtDesc(String parkingLotUuid, Pageable pageable);

    List<ReviewRead> findByParkingLotUuidAndCreatedAtBeforeOrderByCreatedAtDesc(String parkingLotUuid, Instant createdAt,Pageable pageable);

    List<ReviewRead> findAllByUserUuid(String userUuid);

    Optional<ReviewRead> findByReviewUuid(String reviewUuid);
}