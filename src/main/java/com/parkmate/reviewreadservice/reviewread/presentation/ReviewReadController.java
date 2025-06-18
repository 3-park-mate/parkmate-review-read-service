package com.parkmate.reviewreadservice.reviewread.presentation;

import com.parkmate.reviewreadservice.common.response.ApiResponse;
import com.parkmate.reviewreadservice.reviewread.application.ReviewReadService;
import com.parkmate.reviewreadservice.reviewread.dto.response.ReviewListResponseDto;
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
}