package com.parkmate.reviewreadservice.reviewread.vo.response;

import com.parkmate.reviewreadservice.kafka.event.ReactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReactionTypeResponseVo {

    private ReactionType reactionType;

    @Builder
    private ReactionTypeResponseVo(ReactionType reactionType) {
        this.reactionType = reactionType;
    }

    public static ReactionTypeResponseVo of(ReactionType reactionType) {
        return ReactionTypeResponseVo.builder()
                .reactionType(reactionType)
                .build();
    }
}
