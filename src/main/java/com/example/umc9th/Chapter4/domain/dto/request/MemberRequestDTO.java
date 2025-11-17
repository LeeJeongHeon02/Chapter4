package com.example.umc9th.Chapter4.domain.dto.request;

import lombok.Getter;

public class MemberRequestDTO {

    @Getter
    public static class SignUpDto {
        private String name;
        private Integer age;
        private Integer gender;
        private String phoneNumber; // 필수
        private String email;
    }
}
