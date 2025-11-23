package com.example.umc9th.Chapter4.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class MissionResponseDTO {

    @Builder
    @Getter
    public static class MissionAddResultDto {
        private Long missionId;
        private LocalDateTime createdAt;
    }

    // 💡 "미션 도전하기" 응답 DTO
    @Builder
    @Getter
    public static class ChallengeResultDto {
        private Long memberMissionId; // 💡 생성된 MemberMission의 ID
        private LocalDateTime createdAt;
    }
}
