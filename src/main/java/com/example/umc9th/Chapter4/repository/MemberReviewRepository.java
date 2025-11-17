package com.example.umc9th.Chapter4.repository;

import com.example.umc9th.Chapter4.domain.mapping.MemberReview;
import org.springframework.data.jpa.repository.JpaRepository;

// 💡 JpaRepository와 MemberReviewRepositoryCustom을 모두 상속
public interface MemberReviewRepository extends JpaRepository<MemberReview, Long>, MemberReviewRepositoryCustom {
}