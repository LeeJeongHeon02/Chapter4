package com.example.umc9th.Chapter4.service;

import com.example.umc9th.Chapter4.domain.mapping.MemberReview;
import com.example.umc9th.Chapter4.domain.member.Member;
import com.example.umc9th.Chapter4.domain.restaurant.Restaurant;
import com.example.umc9th.Chapter4.repository.MemberRepository;
import com.example.umc9th.Chapter4.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    public Page<Restaurant> getHomeRestaurantsByLocation(Long memberId, int page) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        String locationName = member.getLocation();

        // 2. 페이징 객체를 생성합니다 (10개씩).
        Pageable pageable = PageRequest.of(page, 10);

        // 3. Repository 쿼리 메소드를 호출합니다.
        return restaurantRepository.findByLocationName(locationName, pageable);
    }
}
