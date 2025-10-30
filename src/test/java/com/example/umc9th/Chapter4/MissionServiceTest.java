package com.example.umc9th.Chapter4;

import com.example.umc9th.Chapter4.domain.dto.MissionDetailsDto;
import com.example.umc9th.Chapter4.domain.mapping.MemberMission;
import com.example.umc9th.Chapter4.domain.member.Member;
import com.example.umc9th.Chapter4.domain.mission.Mission;
import com.example.umc9th.Chapter4.domain.restaurant.Restaurant;
import com.example.umc9th.Chapter4.repository.MemberMissionRepository;
import com.example.umc9th.Chapter4.repository.MemberRepository;
import com.example.umc9th.Chapter4.repository.MissionRepository;
import com.example.umc9th.Chapter4.repository.RestaurantRepository;
import com.example.umc9th.Chapter4.service.MissionService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional // 💡 테스트가 끝나면 DB를 롤백하여 원상태로 복구
class MissionServiceTest {

    // 1. 서비스와 Repository를 모두 주입받습니다. (데이터 준비를 위해)
    @Autowired
    private MissionService missionService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private MissionRepository missionRepository;
    @Autowired
    private MemberMissionRepository memberMissionRepository;

    @Test
    void 진행중인_미션_페이징_조회_테스트() {
        // given (테스트 데이터 준비)

        // 1. 회원 저장 (Member 엔티티 사용)
        Member member = memberRepository.save(Member.builder()
                .name("테스터")
                .age(25)
                .gender(0)
                .email("test@test.com")
                .phoneNumber("01012345678")
                .point(0)
                .build());

        // 2. 식당 저장 (Restaurant 엔티티 사용 - @Setter가 있다고 가정)
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName("테스트 식당");
        restaurant = restaurantRepository.save(restaurant);

        // 3. 미션 저장 (Mission 엔티티 사용)
        Mission ongoingMission = new Mission();
        ongoingMission.setRestaurant(restaurant);
        ongoingMission.setMissionContent("진행중 미션 내용");
        ongoingMission.setMissionDeadline(LocalDate.now().plusDays(5));
        ongoingMission.setSuccessScore(100);
        ongoingMission = missionRepository.save(ongoingMission);

        Mission completedMission = new Mission();
        completedMission.setRestaurant(restaurant);
        completedMission.setMissionContent("완료된 미션 내용");
        completedMission.setMissionDeadline(LocalDate.now().minusDays(1));
        completedMission.setSuccessScore(50);
        completedMission = missionRepository.save(completedMission);

        // 4. 회원-미션 매핑 (MemberMission 사용)
        // (1) 진행 중인 미션 연결 (is_finished = false)
        MemberMission mmOngoing = MemberMission.createMemberMission(member, ongoingMission, false);
        memberMissionRepository.save(mmOngoing);

        // (2) 완료된 미션 연결 (is_finished = true)
        MemberMission mmCompleted = MemberMission.createMemberMission(member, completedMission, true);
        memberMissionRepository.save(mmCompleted);


        // when (서비스 호출)
        // 0번째 페이지(첫 번째 페이지)의 10개 데이터를 요청합니다.
        Page<MissionDetailsDto> ongoingPage = missionService.findInProgressMissions(member.getId(), 0);

        // then (결과 검증)

        // 1. 페이징 결과 검증
        assertThat(ongoingPage.getTotalElements()).isEqualTo(1); // 전체 "진행중" 미션은 1개
        assertThat(ongoingPage.getTotalPages()).isEqualTo(1);    // 전체 페이지는 1개
        assertThat(ongoingPage.getNumber()).isEqualTo(0);       // 현재 페이지는 0번
        assertThat(ongoingPage.getSize()).isEqualTo(10);      // 페이지 크기는 10

        // 2. DTO 내용 검증 (쿼리가 DTO로 잘 변환되었는지)
        org.assertj.core.api.Assertions.assertThat(ongoingPage.getContent()).hasSize(1); // 현재 페이지의 컨텐츠 개수는 1개

        MissionDetailsDto resultDto = ongoingPage.getContent().get(0);
        assertThat(resultDto.getMissionContent()).isEqualTo("진행중 미션 내용");
        assertThat(resultDto.getRestaurantName()).isEqualTo("테스트 식당");
        assertThat(resultDto.getSuccessScore()).isEqualTo(100);
    }
}
