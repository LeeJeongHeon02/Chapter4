package com.example.umc9th.Chapter4.domain.mapping;

import com.example.umc9th.Chapter4.domain.member.Member;
import com.example.umc9th.Chapter4.domain.review.Review;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_review")
public class MemberReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Member 엔티티와 다대일(N:1) 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // Review 엔티티와 다대일(N:1) 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    // '의미 있는' 생성자
    private MemberReview(Member member, Review review) {
        this.member = member;
        this.review = review;
    }

    // 정적 팩토리 메서드
    public static MemberReview create(Member member, Review review) {
        return new MemberReview(member, review);
    }
}
