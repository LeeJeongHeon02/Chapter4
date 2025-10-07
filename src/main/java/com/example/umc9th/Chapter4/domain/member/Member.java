package com.example.umc9th.Chapter4.domain.member;


import com.example.umc9th.Chapter4.domain.mapping.MemberFood;
import com.example.umc9th.Chapter4.domain.mapping.MemberMission;
import com.example.umc9th.Chapter4.domain.mapping.MemberReview;
import com.example.umc9th.Chapter4.domain.review.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private String name;

    @Column(name = "member_age", nullable = false)
    private int age;

    @Column(name = "member_gender", nullable = false)
    private int gender; // 0이면 남자, 1이면 여자

    @Column(name = "member_birthday")
    private LocalDate birthDay; // 생년월일 필드

    @Column(name = "member_address")
    private String address;

    @OneToMany(mappedBy = "member")
    private List<MemberReview> memberReviews = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberFood> memberFoods = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberMission> memberMissions = new ArrayList<>();

}
