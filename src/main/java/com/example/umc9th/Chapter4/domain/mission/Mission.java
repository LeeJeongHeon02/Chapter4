package com.example.umc9th.Chapter4.domain.mission;

import com.example.umc9th.Chapter4.domain.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "mission")
@Getter
@Setter
@NoArgsConstructor
public class Mission {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "mission_content", nullable = false, length = 30)
        private String missionContent;

        @Column(name = "mission_deadline", nullable = false)
        private LocalDate missionDeadline;

        @Column(name = "success_score", nullable = false)
        private int successScore;

        // 외래키 설정: Mission과 Restaurant는 N:1 관계
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "restaurant_id", nullable = false)
        private Restaurant restaurant;
}
