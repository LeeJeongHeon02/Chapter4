package com.example.umc9th.Chapter4.domain.review;

import com.example.umc9th.Chapter4.domain.mapping.MemberReview;
import com.example.umc9th.Chapter4.domain.restaurant.Restaurant;
import com.example.umc9th.Chapter4.domain.member.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "review")
@Getter @Setter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 외래키 설정: Review와 Restaurant는 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "review_content", nullable = false, length = 300)
    private String reviewContent;

    @Column(name = "review_photo")
    private String reviewPhoto;

    @OneToMany(mappedBy = "review")
    private List<MemberReview> memberReviews = new ArrayList<>();

    // (생성자 및 정적 팩토리 메서드는 Solution 1과 동일)
    private Review(Restaurant restaurant, int rating, String content) {
        this.restaurant = restaurant;
        this.rating = rating;
        this.reviewContent = content;
        this.createdDate = LocalDate.now();
    }

    public static Review create(Restaurant restaurant, int rating, String content) {
        return new Review(restaurant, rating, content);
    }

    // ⭐️⭐️ 연관관계 편의 메서드 ⭐️⭐️
    // Review를 생성할 때 Member 정보까지 세팅
    public void setMember(Member member) {
        MemberReview memberReview = MemberReview.create(member, this); // 'this'는 newReview 자신
        this.memberReviews.add(memberReview);
        // (만약 Member 객체도 Review 리스트를 가진다면 member.getReviews().add(this)도 필요)
    }
}
