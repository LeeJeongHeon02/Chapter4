package com.example.umc9th.Chapter4;

import com.example.umc9th.Chapter4.domain.dto.MyPageDto;
import com.example.umc9th.Chapter4.domain.member.Member;
import com.example.umc9th.Chapter4.repository.MemberRepository;
import com.example.umc9th.Chapter4.service.MemberService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

// MemberServiceTest.java
@SpringBootTest
@Transactional // 테스트 후 롤백(Rollback)을 위해 필수
class MemberServiceTest {

    @Autowired
    MemberService memberService; // 서비스 주입

    @Test
    @Transactional
    void 회원가입_및_조회_확인() {
        // Given (준비)
        Member member = Member.builder()
                .name("정헌")
                .age(25)
                .gender(0)
                .phoneNumber("01066434511")
                .build();

        // When (실행)
        Long savedId = memberService.join(member);

        // Then (검증)
        // 1. 등록 쿼리 확인: memberService.join() 실행 시 INSERT 쿼리가 나갔는지 콘솔에서 확인

        Member findMember = memberService.findOne(savedId);

        // 2. 조회 쿼리 확인: findOne() 실행 시 SELECT 쿼리가 나갔는지 콘솔에서 확인

        // Assertions.assertEquals(member.getUsername(), findMember.getUsername()); // 실제 검증 로직
    }

    @Test
    @Transactional
    void 쿼리메소드_작동_확인() {
        // Given
        Member member1 = Member.builder()
                .name("SpringUser")
                .age(99)
                .gender(1)
                .phoneNumber("01066434511")
                .build();

        memberService.join(member1);

        // When
        List<Member> foundMembers = memberService.findMembersByName("SpringUser");

        // Then
        // 콘솔에서 WHERE 조건이 포함된 SELECT 쿼리가 나갔는지 확인
    }

//    @Test
//    void findByNameAndGenderIsTrue_작동확인() {
//        // Given
//        Member member1 = new Member("gender1", 99, 1);
//        Member member2 = new Member("gender0", 22, 0);
//        memberService.join(member1);
//        memberService.join(member2);
//
//        // When
//        // findByUsername 쿼리 메소드 실행
//        List<Member> foundMembers1 = memberService.findByNameAndGenderIsTrue("gender1");
//        List<Member> foundMembers2 = memberService.findByNameAndGenderIsTrue("gender0");
//
//        // Then
//        // 콘솔에서 WHERE 조건이 포함된 SELECT 쿼리가 나갔는지 확인
//        // Assertions.assertEquals(1, foundMembers.size());
//    }

    @Test
    @Transactional
    void findByNameAndGender_작동확인() {
        // Given
        Member member1 = Member.builder()
                .name("gender1")
                .age(99)
                .gender(1)
                .phoneNumber("01066434511")
                .build();

        Member member2 = Member.builder()
                .name("gendeer0")
                .age(22)
                .gender(0)
                .phoneNumber("01066434511")
                .build();

        memberService.join(member1);
        memberService.join(member2);

        // When
        // findByUsername 쿼리 메소드 실행
        List<Member> foundMembers1 = memberService.findByNameAndGender("gender1", 1);
        List<Member> foundMembers2 = memberService.findByNameAndGender("gender0", 0);

        // Then
        // 콘솔에서 WHERE 조건이 포함된 SELECT 쿼리가 나갔는지 확인
        // Assertions.assertEquals(1, foundMembers.size());
    }

    @Test
    @Transactional
    void findMemberInfoById_작동확인() {
        //given
        Member member = Member.builder()
                .name("정헌")
                .age(24)
                .gender(0)
                .email("aoc05230@gmail.com")
                .phoneNumber("01066434511")
                .point(10000)
                .build();

        Long savedId = memberService.join(member);

        //when
        MyPageDto myPageInfo = memberService.getMyPageInfo(savedId);
    }
}