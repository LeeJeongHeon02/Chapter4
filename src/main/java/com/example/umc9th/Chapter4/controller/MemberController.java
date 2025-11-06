package com.example.umc9th.Chapter4.controller;


import com.example.umc9th.Chapter4.domain.dto.MemberRequestDTO;
import com.example.umc9th.Chapter4.domain.dto.MemberResponseDTO;
import com.example.umc9th.Chapter4.domain.member.Member;
import com.example.umc9th.Chapter4.global.apiPayload.ApiResponse;
import com.example.umc9th.Chapter4.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc9th.Chapter4.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ApiResponse<MemberResponseDTO.SignUpResultDto> signUp(@RequestBody MemberRequestDTO.SignUpDto request) {
        Member savedMember = memberService.signUp(request);

        // Entity -> Response DTO 변환
        MemberResponseDTO.SignUpResultDto responseDto = MemberResponseDTO.SignUpResultDto
                .builder()
                .memberId(savedMember.getId())
                .createdAt(LocalDateTime.now()) // (주의: 실제로는 Entity의 @CreatedAt 사용)
                .build();

        // 💡 CREATED(201) 코드와 함께 응답
        return ApiResponse.onSuccess(GeneralSuccessCode._CREATED, responseDto);
    }
}
