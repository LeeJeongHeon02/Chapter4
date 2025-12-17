package com.example.umc9th.Chapter4.converter;

import com.example.umc9th.Chapter4.domain.dto.request.MemberRequestDTO;
import com.example.umc9th.Chapter4.domain.dto.response.MemberResponseDTO;
import com.example.umc9th.Chapter4.domain.member.Member;
import com.example.umc9th.Chapter4.global.apiPayload.code.MemberSuccessCode;
import com.example.umc9th.Chapter4.global.auth.enums.Role;

import java.time.LocalDateTime;

public class MemberConverter {

    // entity to DTO
    public static MemberResponseDTO.SignUpResultDto toSignUpResultDto(Member member) {
        return MemberResponseDTO.SignUpResultDto.builder()
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }

    // DTO to entity
    public static Member toMember(MemberRequestDTO.SignUpDto dto,
                                  String password,
                                  Role role
    ) {
        return Member.builder()
                .name(dto.name())
                .email(dto.email())
                .password(password)
                .role(role)
                .age(dto.age())
                .phoneNumber(dto.phoneNumber())
                .build();
    }
}
