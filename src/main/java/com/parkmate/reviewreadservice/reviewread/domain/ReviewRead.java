package com.parkmate.reviewreadservice.reviewread.domain;

import com.parkmate.reviewreadservice.kafka.event.ReactionType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRead  {

    @Id
    private String id;

    @Indexed
    private String reviewUuid;
    private String userUuid;
    private String name;
    private String parkingLotUuid;
    private String content;
    private List<String> imageUrls;
    private int likeCount = 0;
    private int dislikeCount = 0;
    private int rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    private ReviewStatus status = ReviewStatus.ACTIVE;

    @Builder
    public ReviewRead(String id,
                      String reviewUuid,
                      String userUuid,
                      String name,
                      String parkingLotUuid,
                      String content,
                      List<String> imageUrls,
                      int likeCount,
                      int dislikeCount,
                      int rating) {

        this.id = id;
        this.reviewUuid = reviewUuid;
        this.userUuid = userUuid;
        this.name = name;
        this.parkingLotUuid = parkingLotUuid;
        this.content = content;
        this.imageUrls = imageUrls;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.rating = rating;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = ReviewStatus.ACTIVE;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateReaction(ReactionType newReaction,
                               ReactionType previousReaction,
                               LocalDateTime updatedAt) {

        if (previousReaction != null) {
            if (previousReaction == ReactionType.LIKE) this.likeCount--;
            else if (previousReaction == ReactionType.DISLIKE) this.dislikeCount--;
        }

        if (newReaction == ReactionType.LIKE) this.likeCount++;
        else if (newReaction == ReactionType.DISLIKE) this.dislikeCount++;

        this.updatedAt = updatedAt;
    }
    public void updateReview(String content, int rating, List<String> imageUrls, LocalDateTime updatedAt) {
        this.content = content;
        this.rating = rating;
        this.imageUrls = imageUrls;
        this.updatedAt = updatedAt;
    }

    public void markAsDeleted(LocalDateTime deletedAt) {
        this.status = ReviewStatus.DELETED;
        this.deletedAt = deletedAt;
        this.updatedAt = deletedAt;
    }
}