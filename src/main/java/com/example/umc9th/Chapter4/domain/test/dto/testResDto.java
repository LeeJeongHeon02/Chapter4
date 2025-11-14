package com.example.umc9th.Chapter4.domain.test.dto;

import lombok.Builder;
import lombok.Getter;

public class testResDto {

    @Builder
    @Getter
    public static class Testing {
        private String testing;
        private int age;
    }

    @Builder
    @Getter
    public static class Exception {
        private String testString;
        private int testInt;
    }
}
