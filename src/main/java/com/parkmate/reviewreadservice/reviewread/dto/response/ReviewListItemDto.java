package com.parkmate.reviewreadservice.reviewread.dto.response;

import com.parkmate.reviewreadservice.reviewread.domain.ReviewRead;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewListItemDto {

    private String reviewId;
    private String userUuid;
    private String name;
    private String content;
    private List<String> imageUrls;
    private int rating;
    private int likeCount;
    private int dislikeCount;

    @Builder
    private ReviewListItemDto(String reviewId,
                              String userUuid,
                              String name,
                              String content,
                              List<String> imageUrls,
                              int rating,
                              int likeCount,
                              int dislikeCount) {
        this.reviewId = reviewId;
        this.userUuid = userUuid;
        this.name = name;
        this.content = content;
        this.imageUrls = imageUrls;
        this.rating = rating;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

    public static ReviewListItemDto fromEntity(ReviewRead reviewRead) {
        return ReviewListItemDto.builder()
                .reviewId(reviewRead.getReviewId())
                .userUuid(reviewRead.getUserUuid())
                .name(reviewRead.getName())
                .content(reviewRead.getContent())
                .imageUrls(reviewRead.getImageUrls())
                .rating(reviewRead.getRating())
                .likeCount(reviewRead.getLikeCount())
                .dislikeCount(reviewRead.getDislikeCount())
                .build();
    }
}