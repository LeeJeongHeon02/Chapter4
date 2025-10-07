package com.example.umc9th.Chapter4.domain.review;

import com.example.umc9th.Chapter4.domain.mapping.MemberReview;
import com.example.umc9th.Chapter4.domain.restaurant.Restaurant;
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
}
