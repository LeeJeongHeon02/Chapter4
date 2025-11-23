package com.example.umc9th.Chapter4.global.apiPayload.handler;

import com.example.umc9th.Chapter4.global.apiPayload.ApiResponse;
import com.example.umc9th.Chapter4.global.apiPayload.code.BaseErrorCode;
import com.example.umc9th.Chapter4.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.Chapter4.global.apiPayload.exception.GeneralException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. GeneralException 핸들링
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(GeneralException e) {

        BaseErrorCode errorCode = e.getErrorCode();
        ApiResponse<Object> response = ApiResponse.onFailure(errorCode, null);

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Validation error");

        ApiResponse<Object> response = new ApiResponse<>(false, GeneralErrorCode._BAD_REQUEST.getCode(), message, null);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // 2. 그 외 모든 Exception 핸들링
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {

        // (디버깅을 위해 로그 출력)
        e.printStackTrace();

        ApiResponse<Object> response = ApiResponse.onFailure(GeneralErrorCode._INTERNAL_SERVER_ERROR, null);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        BaseErrorCode errorCode = GeneralErrorCode._METHOD_NOT_ALLOWED;
        // e.printStackTrace();
        ApiResponse<Object> response = ApiResponse.onFailure(GeneralErrorCode._METHOD_NOT_ALLOWED, null);

        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }
}
