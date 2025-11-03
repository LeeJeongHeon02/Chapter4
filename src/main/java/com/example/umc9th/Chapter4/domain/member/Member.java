package com.example.umc9th.Chapter4.domain.member;


import com.example.umc9th.Chapter4.domain.mapping.MemberFood;
import com.example.umc9th.Chapter4.domain.mapping.MemberMission;
import com.example.umc9th.Chapter4.domain.mapping.MemberReview;
import com.example.umc9th.Chapter4.domain.review.Review;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member {

    //테스트용 필수파라미터 생성자 -> @Builder 어노테이션으로 변경
//    public Member(String name, int age, int gender) {
//        this.name = name;
//        this.age = age;
//        this.gender = gender;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(name = "member_age", nullable = false)
    private int age;

    @Column(name = "member_gender", nullable = false)
    private int gender; // 0이면 남자, 1이면 여자

    @Column(name = "member_birthday")
    private LocalDate birthDay; // 생년월일 필드

    @Column(name = "member_address")
    private String address;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "member_location")
    private String location;

    @Column(name = "member_point")
    private int point;

    @OneToMany(mappedBy = "member")
    private List<MemberReview> memberReviews = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberFood> memberFoods = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberMission> memberMissions = new ArrayList<>();

}
