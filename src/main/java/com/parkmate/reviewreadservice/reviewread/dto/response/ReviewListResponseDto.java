package com.parkmate.reviewreadservice.reviewread.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewListResponseDto {

    private String cursor;
    private List<ReviewListItemDto> content;

    @Builder
    private ReviewListResponseDto(String cursor, List<ReviewListItemDto> content) {
        this.cursor = cursor;
        this.content = content;
    }

    public static ReviewListResponseDto of(String cursor, List<ReviewListItemDto> content) {
        return ReviewListResponseDto.builder()
                .cursor(cursor)
                .content(content)
                .build();
    }
}