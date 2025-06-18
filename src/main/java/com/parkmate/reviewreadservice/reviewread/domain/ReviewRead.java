package com.parkmate.reviewreadservice.reviewread.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.parkmate.reviewreadservice.common.entity.BaseEntity;
import java.util.List;

@Document(collection = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRead extends BaseEntity {

   @Id
    private String reviewId;

    private String userUuid;
    private String name;
    private String parkingLotUuid;
    private String content;
    private List<String> imageUrls;
    private int likeCount;
    private int dislikeCount;
    private int rating;

    @Builder
    public ReviewRead(String reviewId,
                      String userUuid,
                      String name,
                      String parkingLotUuid,
                      String content,
                      List<String> imageUrls,
                      int likeCount,
                      int dislikeCount,
                      int rating) {

        this.reviewId = reviewId;
        this.userUuid = userUuid;
        this.name = name;
        this.parkingLotUuid = parkingLotUuid;
        this.content = content;
        this.imageUrls = imageUrls;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.rating = rating;
    }

    public void updateName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return this.reviewId;
    }
}