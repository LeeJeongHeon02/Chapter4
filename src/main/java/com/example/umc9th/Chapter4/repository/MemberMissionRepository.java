package com.example.umc9th.Chapter4.repository;

import com.example.umc9th.Chapter4.domain.dto.MissionDetailsDto;
import com.example.umc9th.Chapter4.domain.mapping.MemberMission;
import com.example.umc9th.Chapter4.domain.member.Member;
import com.example.umc9th.Chapter4.domain.mission.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // Pageable 임포트
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {


    @Query(value = "SELECT new com.example.umc9th.Chapter4.domain.dto" +
            ".MissionDetailsDto(r.restaurantName, m.missionContent, m.missionDeadline, m.successScore) " +
            "FROM MemberMission mm " +
            "JOIN mm.mission m " +
            "JOIN m.restaurant r " +
            "WHERE mm.member.id = :memberId AND mm.is_finished = :is_finished")
    Page<MissionDetailsDto> findMissionDetailsByMemberIdAndStatus(
            @Param("memberId") Long memberId,
            @Param("is_finished") boolean isFinished,
            Pageable pageable // 💡 페이징 요청 객체 파라미터 추가
    );

    // 💡 member와 mission 객체로 이미 레코드가 존재하는지 확인
    boolean existsByMemberAndMission(Member member, Mission mission);
}
