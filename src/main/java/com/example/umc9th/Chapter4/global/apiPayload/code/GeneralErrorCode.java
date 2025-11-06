package com.example.umc9th.Chapter4.global.apiPayload.code;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralErrorCode implements BaseErrorCode {

    // (공통)
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 에러, 관리자에게 문의하세요."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON_401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_403", "금지된 요청입니다."),

    _METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_405", "이 메서드는 잘못된 요청입니다"),

    // (도메인별 - 예시)
    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_404_1", "존재하지 않는 회원입니다."),

    // Restaurant
    RESTAURANT_NOT_FOUND(HttpStatus.NOT_FOUND, "RESTAURANT_404_1", "존재하지 않는 식당입니다."),

    // Mission
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION_404_1", "존재하지 않는 미션입니다."),
    MISSION_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "MISSION_400_1", "이미 완료된 미션입니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
