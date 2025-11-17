package com.example.umc9th.Chapter4.repository;

import com.example.umc9th.Chapter4.domain.dto.MyReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberReviewRepositoryCustom {

    // 내가 쓴 리뷰 조회 (동적 필터링 + 페이징)
    Page<MyReviewDto> findMyReviews(Long memberId, Long restaurantId, Integer rating, Pageable pageable);
}