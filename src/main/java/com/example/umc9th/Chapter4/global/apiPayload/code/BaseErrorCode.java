package com.example.umc9th.Chapter4.global.apiPayload.code;


import org.springframework.http.HttpStatus;

// 성공 응답에 대한 기본 규약
public interface BaseErrorCode {

    HttpStatus getStatus();
    String getCode();
    String getMessage();
}
