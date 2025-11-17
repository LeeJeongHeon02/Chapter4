package com.example.umc9th.Chapter4.controller;

import com.example.umc9th.Chapter4.domain.dto.request.MissionRequestDTO;
import com.example.umc9th.Chapter4.domain.dto.response.MissionResponseDTO;
import com.example.umc9th.Chapter4.domain.mapping.MemberMission;
import com.example.umc9th.Chapter4.global.apiPayload.ApiResponse;
import com.example.umc9th.Chapter4.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc9th.Chapter4.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController {

    private final MissionService missionService;

    @PostMapping("/{missionId}/challenge")
    public ApiResponse<MissionResponseDTO.ChallengeResultDto> challenge(
            @PathVariable Long missionId,
            @RequestBody MissionRequestDTO.ChallengeDto request) {

        MemberMission newChallenge = missionService.challengeMission(request.getMemberId(), missionId);

        // Entity -> Response DTO 변환
        MissionResponseDTO.ChallengeResultDto responseDto = MissionResponseDTO.ChallengeResultDto.builder()
                .memberMissionId(newChallenge.getId())
                .createdAt(LocalDateTime.now()) // (Entity @CreatedAt 필드 권장)
                .build();

        // 201 Created 응답
        return ApiResponse.onSuccess(GeneralSuccessCode._CREATED, responseDto);
    }
}