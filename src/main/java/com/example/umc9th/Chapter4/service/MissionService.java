package com.example.umc9th.Chapter4.service;

import com.example.umc9th.Chapter4.domain.dto.MissionDetailsDto;
import com.example.umc9th.Chapter4.domain.dto.request.MissionRequestDTO;
import com.example.umc9th.Chapter4.domain.mapping.MemberMission;
import com.example.umc9th.Chapter4.domain.member.Member;
import com.example.umc9th.Chapter4.domain.mission.Mission;
import com.example.umc9th.Chapter4.domain.restaurant.Restaurant;
import com.example.umc9th.Chapter4.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.Chapter4.global.apiPayload.exception.GeneralException;
import com.example.umc9th.Chapter4.repository.MemberMissionRepository;
import com.example.umc9th.Chapter4.repository.MemberRepository;
import com.example.umc9th.Chapter4.repository.MissionRepository;
import com.example.umc9th.Chapter4.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MemberMissionRepository memberMissionRepository;
    private final MissionRepository missionRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Page<MissionDetailsDto> findInProgressMissions(Long memberId, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        // 1. 진행 중인 미션 조회 (is_finished = false)
        return memberMissionRepository.findMissionDetailsByMemberIdAndStatus(memberId, false, pageable);
    }

    @Transactional
    public Page<MissionDetailsDto> getCompletedMissions(Long memberId, int page) {

        Pageable pageable = PageRequest.of(page, 10);
        // 2. 완료된 미션 조회 (is_finished = true)
        return memberMissionRepository.findMissionDetailsByMemberIdAndStatus(memberId, true, pageable);
    }

    @Transactional
    public Mission addMissionToRestaurant(Long restaurantId, MissionRequestDTO.MissionAddDto request) {

        // 1. 레스토랑 존재 여부 확인
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.RESTAURANT_NOT_FOUND)); // 💡 예외 처리

        Mission mission = new Mission();
        mission.setMissionContent(request.getMissionContent());
        mission.setMissionDeadline(request.getMissionDeadline());
        mission.setSuccessScore(request.getSuccessScore());
        mission.setRestaurant(restaurant); // 💡 연관관계 설정

        // 3. DB에 저장
        return missionRepository.save(mission);
    }

    /**
     * 회원이 미션에 도전하는 로직
     */
    @Transactional
    public MemberMission challengeMission(Long memberId, Long missionId) {

        // 1. 회원과 미션 엔티티 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.MEMBER_NOT_FOUND));

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.MISSION_NOT_FOUND));

        // 2. 중복 도전 여부 확인 (2단계에서 만든 메소드 사용)
        if (memberMissionRepository.existsByMemberAndMission(member, mission)) {
            throw new GeneralException(GeneralErrorCode.MISSION_ALREADY_CHALLENGED);
        }

        // 3. MemberMission 엔티티 생성 (is_finished = false)
        // MemberMission의 정적 팩토리 메소드 사용
        MemberMission newChallenge = MemberMission.createMemberMission(member, mission, false);

        // 4. DB에 저장
        return memberMissionRepository.save(newChallenge);
    }
}
