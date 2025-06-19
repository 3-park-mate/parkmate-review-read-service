package com.parkmate.reviewreadservice.reviewread.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewListResponseDto {

    private String cursor;
    private boolean hasNext;
    private List<ReviewListItemDto> content;

    @Builder
    private ReviewListResponseDto(String cursor,
                                  boolean hasNext,
                                  List<ReviewListItemDto> content) {
        this.cursor = cursor;
        this.hasNext = hasNext;
        this.content = content;
    }

    public static ReviewListResponseDto of(String cursor,
                                           boolean hasNext,
                                           List<ReviewListItemDto> content) {
        return ReviewListResponseDto.builder()
                .cursor(cursor)
                .hasNext(hasNext)
                .content(content)
                .build();
    }
}