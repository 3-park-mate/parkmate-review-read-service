package com.parkmate.reviewreadservice.reviewread.dto.response;

import com.parkmate.reviewreadservice.reviewread.domain.ReviewRead;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewListItemDto {

    private String reviewUuid;
    private String userUuid;
    private String name;
    private String content;
    private List<String> imageUrls;
    private int rating;
    private int likeCount;
    private int dislikeCount;
    private String createdAt;

    @Builder
    private ReviewListItemDto(String reviewUuid,
                              String userUuid,
                              String name,
                              String content,
                              List<String> imageUrls,
                              int rating,
                              int likeCount,
                              int dislikeCount,
                              String createdAt) {
        this.reviewUuid = reviewUuid;
        this.userUuid = userUuid;
        this.name = name;
        this.content = content;
        this.imageUrls = imageUrls;
        this.rating = rating;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.createdAt = createdAt;
    }

    public static ReviewListItemDto fromEntity(ReviewRead reviewRead) {
        return ReviewListItemDto.builder()
                .reviewUuid(reviewRead.getReviewUuid())
                .userUuid(reviewRead.getUserUuid())
                .name(reviewRead.getName())
                .content(reviewRead.getContent())
                .imageUrls(reviewRead.getImageUrls())
                .rating(reviewRead.getRating())
                .likeCount(reviewRead.getLikeCount())
                .dislikeCount(reviewRead.getDislikeCount())
                .createdAt(reviewRead.getCreatedAt() != null
                ? reviewRead.getCreatedAt().toString()
                : null)
                .build();
    }
}