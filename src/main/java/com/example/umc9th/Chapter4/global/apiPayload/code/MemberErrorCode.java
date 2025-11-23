package com.example.umc9th.Chapter4.global.apiPayload.code;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND,
            "MEMBER404_1",
            "해당 사용자를 찾지 못했습니다."),
    PAGE_BAD_REQUEST(HttpStatus.BAD_REQUEST,
            "MEMBER400_1",
            "페이지 번호는 1 이상이어야 합니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
