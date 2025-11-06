package com.example.umc9th.Chapter4.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralSuccessCode implements BaseSuccessCode {

    // (공통)
    _OK(HttpStatus.OK, "COMMON_200", "성공입니다."),
    _CREATED(HttpStatus.CREATED, "COMMON_201", "요청을 성공했으며, 리소스가 생성되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
