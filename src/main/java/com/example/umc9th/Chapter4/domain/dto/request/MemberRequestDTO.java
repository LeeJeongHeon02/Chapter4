package com.example.umc9th.Chapter4.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class MemberRequestDTO {

    public record SignUpDto (
            @NotBlank
            String name,
            Integer age,
            Integer gender,
            String phoneNumber, // 필수
            @Email
            String email,
            @NotBlank
            String password
    ) {}
}
