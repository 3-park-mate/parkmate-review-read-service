package com.parkmate.reviewreadservice.kafka.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewDeletedEvent {

    private String reviewUuid;
    private LocalDateTime deletedAt;
    private LocalDateTime timestamp;
}