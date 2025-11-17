package com.example.umc9th.Chapter4.service;


import com.example.umc9th.Chapter4.converter.MemberConverter;
import com.example.umc9th.Chapter4.domain.dto.request.MemberRequestDTO;
import com.example.umc9th.Chapter4.domain.dto.MyPageDto;
import com.example.umc9th.Chapter4.domain.dto.response.MemberResponseDTO;
import com.example.umc9th.Chapter4.domain.member.Member;
import com.example.umc9th.Chapter4.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.Chapter4.global.apiPayload.exception.GeneralException;
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
    public Member signUp(MemberRequestDTO.SignUpDto request) {

        // DTO -> Entity 변환 (Builder 사용)
        Member newMember = Member.builder()
                .name(request.getName())
                .age(request.getAge())
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .point(0) // 기본 포인트 0
                .build();

        return memberRepository.save(newMember);
    }

    public MemberResponseDTO.SignUpResultDto signUp2(MemberRequestDTO.SignUpDto dto) {
        Member member = MemberConverter.toMember(dto);
        memberRepository.save(member);


        // 비즈니스 로직 작성


        return MemberConverter.toSignUpResultDto(member);
    }

    @Transactional
    public Long join(Member member) {
        Member save = memberRepository.save(member);
        return save.getId();
    }

    @Transactional
    public Member findOne(Long id) {
        return memberRepository.findById(id)
                // 💡 CustomException을 던지도록 수정
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.MEMBER_NOT_FOUND));
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
