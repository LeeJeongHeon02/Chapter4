package com.example.umc9th.Chapter4.repository;

import com.example.umc9th.Chapter4.domain.dto.MyReviewDto;
// 1단계에서 생성한 QMyReviewDto를 import
import com.example.umc9th.Chapter4.domain.dto.QMyReviewDto;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

// Q-Type 클래스들을 static import로 가져오면 편리합니다.
import static com.example.umc9th.Chapter4.domain.member.QMember.member;
import static com.example.umc9th.Chapter4.domain.mapping.QMemberReview.memberReview;
import static com.example.umc9th.Chapter4.domain.review.QReview.review;
import static com.example.umc9th.Chapter4.domain.restaurant.QRestaurant.restaurant;

@RequiredArgsConstructor
public class MemberReviewRepositoryCustomImpl implements MemberReviewRepositoryCustom {

    // 0단계에서 Bean으로 등록한 JPAQueryFactory를 주입받습니다.
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MyReviewDto> findMyReviews(Long memberId, Long restaurantId, Integer rating, Pageable pageable) {

        // 1. DTO 프로젝션(Projections)과 조인(Join) 설정
        List<MyReviewDto> content = queryFactory
                .select(new QMyReviewDto(
                        member.name,
                        review.rating,
                        review.reviewContent,
                        review.createdDate
                ))
                .from(memberReview)
                .join(memberReview.review, review)
                .join(memberReview.member, member)
                .where(
                        // 2. 기본 조건 (내가 쓴 리뷰)
                        memberReview.member.id.eq(memberId),

                        // 3. 동적 필터링 조건 (가게 ID, 별점)
                        restaurantEq(restaurantId),
                        ratingEq(rating)
                )
                // 4. 페이징 처리
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch(); // 쿼리 실행 및 DTO 리스트 반환

        // 5. 전체 카운트 조회 (페이징을 위한)
        // (최적화를 위해 DTO 프로젝션 없이 카운트만 가져옵니다)
        Long totalCount = queryFactory
                .select(memberReview.count())
                .from(memberReview)
                .join(memberReview.review, review) // 💡Impl에서는 조인 별칭(review)이 필요합니다.
                .where(
                        memberReview.member.id.eq(memberId),
                        restaurantEq(restaurantId),
                        ratingEq(rating)
                )
                .fetchOne();

        // 6. Page 객체로 변환하여 반환
        return new PageImpl<>(content, pageable, totalCount);
    }

    // --- 동적 쿼리를 위한 BooleanExpression ---

    // 가게 ID 필터 (null이면 조건 무시)
    private BooleanExpression restaurantEq(Long restaurantId) {
        return restaurantId != null ? review.restaurant.id.eq(restaurantId) : null;
    }

    // 별점 필터 (null이면 조건 무시)
    private BooleanExpression ratingEq(Integer rating) {
        return rating != null ? review.rating.eq(rating) : null;
    }
}