// Chapter4/repository/RestaurantRepository.java
package com.example.umc9th.Chapter4.repository;

import com.example.umc9th.Chapter4.domain.restaurant.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    /**
     * 홈 화면 쿼리: 특정 지역(Location)의 이름(name)을 기준으로
     * 레스토랑 목록을 페이징 조회합니다.
     *
     * @param locationName (예: "강남구")
     * @param pageable (페이지 번호, 페이지당 개수(10) 정보)
     * @return Page<Restaurant>
     */
    Page<Restaurant> findByLocationName(String locationName, Pageable pageable);
}