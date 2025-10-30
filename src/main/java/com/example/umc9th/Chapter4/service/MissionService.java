package com.example.umc9th.Chapter4.service;

import com.example.umc9th.Chapter4.domain.dto.MissionDetailsDto;
import com.example.umc9th.Chapter4.repository.MemberMissionRepository;
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

}
