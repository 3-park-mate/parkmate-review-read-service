package com.parkmate.reviewreadservice.reviewread.application;

import com.parkmate.reviewreadservice.common.response.ApiResponse;
import com.parkmate.reviewreadservice.kafka.event.ReactionType;
import com.parkmate.reviewreadservice.reviewread.domain.ReviewReactionRead;
import com.parkmate.reviewreadservice.reviewread.domain.ReviewRead;
import com.parkmate.reviewreadservice.reviewread.dto.response.ReviewListResponseDto;
import com.parkmate.reviewreadservice.reviewread.dto.response.ReviewListItemDto;
import com.parkmate.reviewreadservice.reviewread.dto.response.ReviewSummaryResponseDto;
import com.parkmate.reviewreadservice.reviewread.infrastructure.ReviewMongoRepository;
import com.parkmate.reviewreadservice.reviewread.infrastructure.ReviewReactionReadRepository;
import com.parkmate.reviewreadservice.reviewread.infrastructure.client.ReviewSummaryFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewReadServiceImpl implements ReviewReadService {

    private final ReviewMongoRepository reviewMongoRepository;
    private final ReviewReactionReadRepository reviewReactionReadRepository;
    private final ReviewSummaryFeignClient reviewSummaryFeignClient;
    private final MongoTemplate mongoTemplate;

    @Transactional
    @Override
    public ReviewListResponseDto getReviews(String parkingLotUuid, String cursor, int size) {
        Criteria criteria = new Criteria()
                .andOperator(
                        Criteria.where("parkingLotUuid").is(parkingLotUuid),
                        Criteria.where("status").is("ACTIVE")
                );

        if (cursor != null) {
            LocalDateTime cursorTime = LocalDateTime.parse(cursor, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
            criteria = criteria.and("createdAt").lt(cursorTime);
        }

        Query query = new Query(criteria)
                .with(Sort.by(Sort.Direction.DESC, "createdAt"))
                .limit(size + 1);

        List<ReviewRead> results = mongoTemplate.find(query, ReviewRead.class);

        boolean hasNext = results.size() > size;
        List<ReviewRead> page = hasNext ? results.subList(0, size) : results;

        String nextCursor = page.isEmpty() ? null :
                page.get(page.size() - 1).getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));

        List<ReviewListItemDto> content = page.stream()
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

    @Transactional(readOnly = true)
    @Override
    public ReviewSummaryResponseDto getReviewSummary(String parkingLotUuid) {
        ApiResponse<ReviewSummaryResponseDto> response = reviewSummaryFeignClient.getReviewSummary(parkingLotUuid);
        return response.getData();
    }
}