package com.example.umc9th.Chapter4.domain.test.exception.code;

import com.example.umc9th.Chapter4.global.apiPayload.code.BaseErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TestErrorCode implements BaseErrorCode {

    // For test
    TEST_EXCEPTION(HttpStatus.BAD_REQUEST, "TEST400_1", "이거는 첫번째 테스트"),
    METHOD_EXCEPTION(HttpStatus.METHOD_NOT_ALLOWED, "COMMON405", "메서드오류긴한데...")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
