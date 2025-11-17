package com.example.umc9th.Chapter4.converter;

import com.example.umc9th.Chapter4.domain.dto.request.MemberRequestDTO;
import com.example.umc9th.Chapter4.domain.dto.response.MemberResponseDTO;
import com.example.umc9th.Chapter4.domain.member.Member;
import com.example.umc9th.Chapter4.global.apiPayload.code.MemberSuccessCode;

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
    public static Member toMember(MemberRequestDTO.SignUpDto dto) {
        return Member.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .build();
    }
}
