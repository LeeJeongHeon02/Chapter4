package com.example.umc9th.Chapter4.service;

import com.example.umc9th.Chapter4.domain.location.Location;
import com.example.umc9th.Chapter4.domain.dto.request.RestaurantRequestDTO;
import com.example.umc9th.Chapter4.domain.restaurant.Restaurant;
import com.example.umc9th.Chapter4.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.Chapter4.global.apiPayload.exception.GeneralException;
import com.example.umc9th.Chapter4.repository.LocationRepository;
import com.example.umc9th.Chapter4.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository; //
    private final LocationRepository locationRepository; //

    @Transactional
    public Restaurant registerRestaurant(RestaurantRequestDTO.RegisterDto request) {

        // 1. DTO에서 받은 locationId로 Location 엔티티를 조회
        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.LOCATION_NOT_FOUND));

        // 2. DTO -> Entity 변환
        // (Restaurant 엔티티에는 @Builder가 없으므로 @Data를 이용한 기본 생성자/Setter 사용)
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setRestaurantName(request.getRestaurantName());
        newRestaurant.setRestaurantAddress(request.getRestaurantAddress());
        newRestaurant.setLocation(location); // 💡 조회한 Location 엔티티를 설정
        // (restaurantRuntime은 null)

        // 3. Repository를 통해 DB에 저장
        return restaurantRepository.save(newRestaurant);
    }
}
