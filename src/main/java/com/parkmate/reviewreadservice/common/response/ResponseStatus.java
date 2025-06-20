package com.parkmate.reviewreadservice.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    // ✅ 2xx: 성공

    // ❌ 4xx: 클라이언트 오류
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, false, 404, "해당 리뷰를 찾을 수 없습니다."),


    // ❗ 5xx: 서버 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 500, "서버 내부 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final int code;
    private final String message;
}