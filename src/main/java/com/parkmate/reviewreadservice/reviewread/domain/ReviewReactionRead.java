package com.parkmate.reviewreadservice.reviewread.domain;

import com.parkmate.reviewreadservice.kafka.event.ReactionType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "review_reaction_read")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewReactionRead {

    @Id
    private String id;

    private String reviewUuid;
    private String userUuid;
    private ReactionType reactionType;
    private LocalDateTime reactedAt;

    @Builder
    public ReviewReactionRead(String reviewUuid,
                              String userUuid,
                              ReactionType reactionType,
                              LocalDateTime reactedAt) {

        this.reviewUuid = reviewUuid;
        this.userUuid = userUuid;
        this.reactionType = reactionType;
        this.reactedAt = reactedAt != null ? reactedAt : LocalDateTime.now();
    }

    public void changeReaction(ReactionType newReaction, LocalDateTime updatedAt) {
        this.reactionType = newReaction;
        this.reactedAt = updatedAt;
    }
}
