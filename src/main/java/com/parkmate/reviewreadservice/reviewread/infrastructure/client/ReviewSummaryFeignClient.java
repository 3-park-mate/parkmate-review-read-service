package com.parkmate.reviewreadservice.reviewread.infrastructure.client;

import com.parkmate.reviewreadservice.common.response.ApiResponse;
import com.parkmate.reviewreadservice.reviewread.dto.response.ReviewSummaryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "batch-service")
public interface ReviewSummaryFeignClient {

    @GetMapping("/internal/batch/review-summary")
    ApiResponse<ReviewSummaryResponseDto> getReviewSummary(@RequestParam String parkingLotUuid);
}
