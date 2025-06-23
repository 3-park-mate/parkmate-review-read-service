package com.parkmate.reviewreadservice.reviewread.application;

import com.parkmate.reviewreadservice.common.exception.BaseException;
import com.parkmate.reviewreadservice.common.response.ResponseStatus;
import com.parkmate.reviewreadservice.kafka.event.ReactionType;
import com.parkmate.reviewreadservice.kafka.event.ReviewCreatedEvent;
import com.parkmate.reviewreadservice.kafka.event.CreateReviewJoinUserEvent;
import com.parkmate.reviewreadservice.reviewread.domain.ReviewRead;
import com.parkmate.reviewreadservice.reviewread.infrastructure.ReviewMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewReadIntegrationServiceImpl implements ReviewReadIntegrationService {

    private final ReviewMongoRepository reviewMongoRepository;

    @Transactional
    @Override
    public void createReviewReadDocument(ReviewCreatedEvent reviewEvent, CreateReviewJoinUserEvent userEvent) {
        ReviewRead reviewRead = ReviewRead.builder()
                .reviewUuid(reviewEvent.getReviewUuid())
                .userUuid(reviewEvent.getUserUuid())
                .name(userEvent.getName())
                .parkingLotUuid(reviewEvent.getParkingLotUuid())
                .content(reviewEvent.getContent())
                .rating(reviewEvent.getRating())
                .imageUrls(reviewEvent.getImageUrls())
                .likeCount(reviewEvent.getLikeCount())
                .dislikeCount(reviewEvent.getDislikeCount())
                .build();

        reviewMongoRepository.save(reviewRead);
        log.info("[Mongo] 저장 완료: {}", reviewRead.getReviewUuid());
    }

    @Transactional
    @Override
    public void updateUserNameInReviews(String userUuid, String name) {
        List<ReviewRead> reviews = reviewMongoRepository.findAllByUserUuid(userUuid);

        if (reviews.isEmpty()) {
            log.info("[Mongo] userUuid에 해당하는 리뷰 없음: {}", userUuid);
            return;
        }

        for (ReviewRead review : reviews) {
            review.updateName(name);
        }

        reviewMongoRepository.saveAll(reviews);
        log.info("[Mongo] 사용자 이름 일괄 업데이트 완료 - userUuid: {}, newName: {}", userUuid, name);
    }

    @Transactional
    @Override
    public void updateReaction(String reviewUuid, ReactionType newReaction, ReactionType previousReaction, LocalDateTime updatedAt) {
        ReviewRead reviewRead = reviewMongoRepository.findByReviewUuid(reviewUuid)
                .orElseThrow(() -> new BaseException(ResponseStatus.REVIEW_NOT_FOUND));

        reviewRead.updateReaction(newReaction, previousReaction, updatedAt);
        reviewMongoRepository.save(reviewRead);

        log.info("[Mongo] 리액션 정보 업데이트 완료: reviewUuid={}, like={}, dislike={}",
                reviewUuid, reviewRead.getLikeCount(), reviewRead.getDislikeCount());
    }
}