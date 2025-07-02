package com.parkmate.reviewreadservice.kafka.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewUpdatedEvent {
    private String reviewUuid;
    private String content;
    private int rating;
    private List<String> imageUrls;
    private LocalDateTime updatedAt;
    private LocalDateTime timestamp;
}