package com.example.umc9th.Chapter4.service;

import com.example.umc9th.Chapter4.domain.dto.MyReviewDto;
import com.example.umc9th.Chapter4.repository.MemberReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // CUD가 없으므로 readOnly로 성능 최적화
public class ReviewService {

    private final MemberReviewRepository memberReviewRepository;

    public Page<MyReviewDto> getMyReviews(Long memberId, Long restaurantId, Integer rating, Pageable pageable) {
        // Repository에 구현된 QueryDSL 메소드를 호출
        return memberReviewRepository.findMyReviews(memberId, restaurantId, rating, pageable);
    }
}