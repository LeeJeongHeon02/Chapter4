// Chapter4/repository/MissionRepository.java
package com.example.umc9th.Chapter4.repository;

import com.example.umc9th.Chapter4.domain.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    // 💡 JpaRepository<엔티티 클래스, PK 타입>
}
