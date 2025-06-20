package com.parkmate.reviewreadservice.reviewread.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Document(collection = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewRead  {

   @Id
    private String reviewUuid;

    private String userUuid;
    private String name;
    private String parkingLotUuid;
    private String content;
    private List<String> imageUrls;
    private int likeCount;
    private int dislikeCount;
    private int rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ReviewRead(String reviewUuid,
                      String userUuid,
                      String name,
                      String parkingLotUuid,
                      String content,
                      List<String> imageUrls,
                      int likeCount,
                      int dislikeCount,
                      int rating) {

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
    }

    public void updateName(String name) {
        this.name = name;
    }

}