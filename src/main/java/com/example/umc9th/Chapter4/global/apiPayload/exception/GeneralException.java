package com.example.umc9th.Chapter4.global.apiPayload.exception;

import com.example.umc9th.Chapter4.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
// 우리가 만들 CustomException의 최상위 부모
public class GeneralException extends RuntimeException {

    private BaseErrorCode errorCode;
}
