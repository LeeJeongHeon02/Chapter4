package com.example.umc9th.Chapter4.domain.test.convertor;

import com.example.umc9th.Chapter4.domain.test.dto.testResDto;

public class TestConverter {

    // 객체 -> DTO
    public static testResDto.Testing toTestingDTO(
            String testing
    ) {
        return testResDto.Testing.builder()
                .testing(testing)
                .age(23)
                .build();
    }

    // 객체 -> DTO
    public static testResDto.Exception toExceptionDTO(
            String testing
    ){
        return testResDto.Exception.builder()
                .testString(testing)
                .testInt(66)
                .build();
    }
}
