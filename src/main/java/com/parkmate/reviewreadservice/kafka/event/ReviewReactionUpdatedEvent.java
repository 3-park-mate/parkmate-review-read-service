package com.parkmate.reviewreadservice.kafka.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewReactionUpdatedEvent {

    private String reviewUuid;
    private ReactionType reactionType;
    private ReactionType previousReactionType;
    private LocalDateTime timestamp;

    @Builder
    private ReviewReactionUpdatedEvent(
            String reviewUuid,
            ReactionType reactionType,
            ReactionType previousReactionType,
            LocalDateTime timestamp
    ) {
        this.reviewUuid = reviewUuid;
        this.reactionType = reactionType;
        this.previousReactionType = previousReactionType;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }
}
