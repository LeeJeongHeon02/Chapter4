package com.example.umc9th.Chapter4.domain.mapping;

import com.example.umc9th.Chapter4.domain.member.Member;
import com.example.umc9th.Chapter4.domain.mission.Mission;
import com.example.umc9th.Chapter4.domain.review.Review;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_mission")
public class MemberMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Member 엔티티와 다대일(N:1) 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // mission 엔티티와 다대일(N:1) 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Column
    private boolean is_finished;

    // 💡 [해결책] 생성자 대신 이 메소드를 사용합니다.
    public static MemberMission createMemberMission(Member member, Mission mission, boolean isFinished) {
        MemberMission memberMission = new MemberMission();
        memberMission.setMember(member);
        memberMission.setMission(mission);
        memberMission.set_finished(isFinished);
        return memberMission;
    }
}
