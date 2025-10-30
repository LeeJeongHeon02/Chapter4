package com.example.umc9th.Chapter4.service;


import com.example.umc9th.Chapter4.domain.dto.MyPageDto;
import com.example.umc9th.Chapter4.domain.member.Member;
import com.example.umc9th.Chapter4.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        Member save = memberRepository.save(member);
        return save.getId();
    }

    @Transactional
    public Member findOne(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
    }

    @Transactional
    public List<Member> findMembersByName(String name) {
        return memberRepository.findByName(name);
    }

//    @Transactional
//    public List<Member> findByNameAndGenderIsTrue(String name) {
//        return memberRepository.findByNameAndGenderIsTrue(name);
//    }

    @Transactional
    public List<Member> findByNameAndGender(String name, int gender) {
        return memberRepository.findByNameAndGender(name, gender);
    }

    @Transactional
    public MyPageDto getMyPageInfo(Long memberId) {
        return memberRepository.findMemberInfoById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));
    }
}
