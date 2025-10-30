package com.example.umc9th.Chapter4.repository;

import com.example.umc9th.Chapter4.domain.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
