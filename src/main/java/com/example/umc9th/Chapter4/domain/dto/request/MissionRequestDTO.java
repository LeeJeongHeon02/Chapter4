package com.example.umc9th.Chapter4.domain.dto.request;

import lombok.Getter;

import java.time.LocalDate;

public class MissionRequestDTO {

    @Getter
    public static class MissionAddDto {
        private String missionContent;
        private LocalDate missionDeadline;
        private Integer successScore;
    }

    // 💡 "미션 도전하기" 요청 DTO
    @Getter
    public static class ChallengeDto {
        private Long memberId;
    }
}
