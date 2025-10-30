// (테스트 폴더)/Chapter4/service/HomeServiceTest.java
package com.example.umc9th.Chapter4;

import com.example.umc9th.Chapter4.domain.location.Location;
import com.example.umc9th.Chapter4.domain.member.Member;
import com.example.umc9th.Chapter4.domain.restaurant.Restaurant;
import com.example.umc9th.Chapter4.repository.LocationRepository;
import com.example.umc9th.Chapter4.repository.MemberRepository;
import com.example.umc9th.Chapter4.repository.RestaurantRepository;
import com.example.umc9th.Chapter4.service.HomeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class HomeServiceTest {

    @Autowired
    private HomeService homeService;

    // "Given" (데이터 준비)를 위한 Repository 주입
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private LocationRepository locationRepository;

    @Test
    void 회원_위치_기반_레스토랑_페이징_조회_테스트() {
        // given
        // 1. 지역(Location) 2개 생성 및 저장
        Location gangnam = new Location();
        gangnam.setName("강남구");
        locationRepository.save(gangnam);

        Location songpa = new Location();
        songpa.setName("송파구");
        locationRepository.save(songpa);

        // 2. 회원(Member) 생성 및 저장 (위치를 "강남구"로 설정)
        Member member = Member.builder()
                .name("테스터")
                .location("강남구") // 💡 사용자의 현재 위치
                .age(20).gender(0).email("test@test.com").phoneNumber("010").point(0)
                .build();
        memberRepository.save(member);

        // 3. 레스토랑(Restaurant) 생성 (총 15개)
        // "강남구" 12개
        for (int i = 0; i < 12; i++) {
            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurantName("강남구 식당 " + i);
            restaurant.setLocation(gangnam); // 💡 강남구 위치 설정
            restaurantRepository.save(restaurant);
        }
        // "송파구" 3개
        for (int i = 0; i < 3; i++) {
            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurantName("송파구 식당 " + i);
            restaurant.setLocation(songpa); // 💡 송파구 위치 설정
            restaurantRepository.save(restaurant);
        }

        // when
        // 회원의 ID를 기준으로 0번째 페이지(첫 페이지)를 10개씩 조회
        Page<Restaurant> resultPage = homeService.getHomeRestaurantsByLocation(member.getId(), 0);

        // then
        // 1. 필터링 검증: "강남구" 레스토랑만 조회되었는지 확인
        assertThat(resultPage.getTotalElements()).isEqualTo(12); // "송파구" 3개는 제외됨

        // 2. 페이징 검증 (10개씩)
        assertThat(resultPage.getTotalPages()).isEqualTo(2);    // 12개 / 10개 = 2페이지
        assertThat(resultPage.getNumber()).isEqualTo(0);       // 현재 페이지 0번
        assertThat(resultPage.getContent()).hasSize(10);      // 0번 페이지에는 10개

        // 3. 내용 검증 (첫 번째 항목이 "강남구"인지)
        assertThat(resultPage.getContent().get(0).getLocation().getName()).isEqualTo("강남구");
    }
}