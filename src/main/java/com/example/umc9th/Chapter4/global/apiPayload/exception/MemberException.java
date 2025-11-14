package com.example.umc9th.Chapter4.global.apiPayload.exception;

import com.example.umc9th.Chapter4.global.apiPayload.code.BaseErrorCode;

public class MemberException extends GeneralException {

    public MemberException(BaseErrorCode code) {
        super(code);
    }
}
