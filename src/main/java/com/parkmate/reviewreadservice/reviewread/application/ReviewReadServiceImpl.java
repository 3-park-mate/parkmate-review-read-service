package com.parkmate.reviewreadservice.reviewread.application;

import com.parkmate.reviewreadservice.kafka.event.ReactionType;
import com.parkmate.reviewreadservice.reviewread.domain.ReviewReactionRead;
import com.parkmate.reviewreadservice.reviewread.domain.ReviewRead;
import com.parkmate.reviewreadservice.reviewread.dto.response.ReviewListResponseDto;
import com.parkmate.reviewreadservice.reviewread.dto.response.ReviewListItemDto;
import com.parkmate.reviewreadservice.reviewread.infrastructure.ReviewMongoRepository;
import com.parkmate.reviewreadservice.reviewread.infrastructure.ReviewReactionReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewReadServiceImpl implements ReviewReadService {

    private final ReviewMongoRepository reviewMongoRepository;
    private final ReviewReactionReadRepository reviewReactionReadRepository;

    @Transactional
    @Override
    public ReviewListResponseDto getReviews(String parkingLotUuid, String cursor, int size) {
        Pageable pageable = PageRequest.of(0, size + 1); // +1 for hasNext check
        List<ReviewRead> reviews;

        if (cursor == null) {
            reviews = reviewMongoRepository.findByParkingLotUuidOrderByCreatedAtDesc(parkingLotUuid, pageable);
        } else {
            Instant cursorInstant = LocalDateTime.parse(cursor)
                    .atZone(ZoneId.systemDefault())
                    .toInstant();
            reviews = reviewMongoRepository.findByParkingLotUuidAndCreatedAtBeforeOrderByCreatedAtDesc(
                    parkingLotUuid, cursorInstant, pageable);
        }

        boolean hasNext = reviews.size() > size;
        List<ReviewRead> pageReviews = hasNext ? reviews.subList(0, size) : reviews;

        String nextCursor = pageReviews.isEmpty() ? null : pageReviews.get(pageReviews.size() - 1).getCreatedAt().toString();

        List<ReviewListItemDto> content = pageReviews.stream()
                .map(ReviewListItemDto::fromEntity)
                .toList();

        return ReviewListResponseDto.of(nextCursor, hasNext, content);
    }

    @Transactional(readOnly = true)
    @Override
    public ReactionType getUserReactionType(String reviewUuid, String userUuid) {

        return reviewReactionReadRepository.findByReviewUuidAndUserUuid(reviewUuid, userUuid)
                .map(ReviewReactionRead::getReactionType)
                .orElse(ReactionType.NONE);
    }
}