package com.example.umc9th.Chapter4.repository;

import com.example.umc9th.Chapter4.domain.dto.MyPageDto;
import com.example.umc9th.Chapter4.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByName(String name);
    //List<Member> findByNameAndGenderIsTrue(String name);
    List<Member> findByNameAndGender(String name, int gender);

    Optional<MyPageDto> findMemberInfoById(Long id);
}
