package com.parkmate.reviewreadservice.reviewread.presentation;

import com.parkmate.reviewreadservice.common.response.ApiResponse;
import com.parkmate.reviewreadservice.reviewread.application.ReviewReadService;
import com.parkmate.reviewreadservice.reviewread.dto.response.ReviewListResponseDto;
import com.parkmate.reviewreadservice.reviewread.vo.response.ReactionTypeResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewReadController {

    private final ReviewReadService reviewReadService;

    @Operation(
            summary = "주차장 리뷰 목록 조회",
            description = """
                        주차장 UUID로 등록된 리뷰 목록을 커서 기반으로 조회합니다.
                    
                        - `parkingLotUuid`는 필수입니다.
                        - `cursor`는 이전 페이지의 마지막 리뷰 작성시간 (ISO-8601), 없으면 최신순 조회 시작
                        - `size`는 조회할 리뷰 개수, 기본값은 10
                        - 응답에 리뷰 작성자의 `name`도 포함되어 Kafka 이벤트 반영 여부 확인 가능합니다.
                    """,
            tags = {"REVIEW-READ-SERVICE"}
    )
    @GetMapping
    public ApiResponse<ReviewListResponseDto> getReviews(
            @RequestParam String parkingLotUuid,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.of(
                HttpStatus.OK,
                "리뷰 목록 조회 성공",
                reviewReadService.getReviews(parkingLotUuid, cursor, size)
        );
    }

    @Operation(
            summary = "사용자 리액션 타입 조회",
            description = """
                특정 리뷰에 대해 사용자가 남긴 리액션 타입을 조회합니다.

                - `reviewUuid`는 리뷰 고유 UUID입니다.
                - `X-User-UUID`는 헤더로 전달되는 사용자 고유 UUID입니다.
                - 응답은 사용자가 해당 리뷰에 남긴 리액션 타입(예: LIKE, DISLIKE, NONE)입니다.
            """,
            tags = {"REVIEW-READ-SERVICE"}
    )
    @GetMapping("/{reviewUuid}/reaction")
    public ApiResponse<ReactionTypeResponseVo> getUserReactionType(
            @PathVariable String reviewUuid,
            @RequestHeader("X-User-UUID") String userUuid
    ) {
        return ApiResponse.of(
                HttpStatus.OK,
                "사용자 리액션 조회 성공",
                ReactionTypeResponseVo.of(reviewReadService.getUserReactionType(reviewUuid, userUuid))
        );
    }
}