package com.example.umc9th.Chapter4.service;

import com.example.umc9th.Chapter4.domain.dto.MyReviewDto;
import com.example.umc9th.Chapter4.domain.dto.request.ReviewRequestDTO;
import com.example.umc9th.Chapter4.domain.member.Member;
import com.example.umc9th.Chapter4.domain.restaurant.Restaurant;
import com.example.umc9th.Chapter4.domain.review.Review;
import com.example.umc9th.Chapter4.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.Chapter4.global.apiPayload.exception.GeneralException;
import com.example.umc9th.Chapter4.repository.MemberRepository;
import com.example.umc9th.Chapter4.repository.MemberReviewRepository;
import com.example.umc9th.Chapter4.repository.RestaurantRepository;
import com.example.umc9th.Chapter4.repository.ReviewRepository;
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

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    public Page<MyReviewDto> getMyReviews(Long memberId, Long restaurantId, Integer rating, Pageable pageable) {
        // Repository에 구현된 QueryDSL 메소드를 호출
        return memberReviewRepository.findMyReviews(memberId, restaurantId, rating, pageable);
    }


    // 💡 리뷰 작성 로직
    @Transactional // (readOnly = false)
    public Review writeReview(ReviewRequestDTO.WriteDto request) {

        // 1. 회원과 식당 엔티티 조회
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.MEMBER_NOT_FOUND));

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.RESTAURANT_NOT_FOUND));

        // 2. Review 엔티티 생성 및 저장
        Review newReview = Review.create(
                restaurant,
                request.getRating(),
                request.getContent()
        );
        // 3. 연관관계 설정 (편의 메서드)
        // 이 메서드 내부에서 MemberReview가 생성되고 Review의 리스트에 추가됨
        newReview.setMember(member);

        // 4. Review만 저장 (CascadeType.ALL 옵션 덕분에 MemberReview는 자동 저장됨)
        reviewRepository.save(newReview);

        return newReview;
    }
}