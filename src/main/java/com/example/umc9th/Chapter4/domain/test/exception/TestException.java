package com.example.umc9th.Chapter4.domain.test.exception;

import com.example.umc9th.Chapter4.global.apiPayload.code.BaseErrorCode;
import com.example.umc9th.Chapter4.global.apiPayload.exception.GeneralException;
import lombok.AllArgsConstructor;
import lombok.Getter;


public class TestException extends GeneralException {

    public TestException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
