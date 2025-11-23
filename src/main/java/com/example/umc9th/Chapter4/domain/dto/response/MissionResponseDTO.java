package com.example.umc9th.Chapter4.domain.dto.response;

import com.example.umc9th.Chapter4.domain.dto.MissionDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionPreviewDTO {
        private Integer successScore;
        private LocalDate missionDeadline;
        private String missionContent;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionListDTO {
        private List<MissionPreviewDTO> missionList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InProgressMissionListDTO {
        private List<MissionDetailsDto> missionList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }
}
