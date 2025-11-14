package com.example.umc9th.Chapter4.controller;

import com.example.umc9th.Chapter4.domain.dto.request.RestaurantRequestDTO;
import com.example.umc9th.Chapter4.domain.dto.response.RestaurantResponseDTO;
import com.example.umc9th.Chapter4.domain.restaurant.Restaurant;
import com.example.umc9th.Chapter4.global.apiPayload.ApiResponse;
import com.example.umc9th.Chapter4.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc9th.Chapter4.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/")
    public ApiResponse<RestaurantResponseDTO.RegisterResultDto> register(
            @RequestBody RestaurantRequestDTO.RegisterDto request) {

        // 1. Service를 호출하여 로직 수행
        Restaurant savedRestaurant = restaurantService.registerRestaurant(request);

        // 2. Entity -> Response DTO 변환
        RestaurantResponseDTO.RegisterResultDto responseDto = RestaurantResponseDTO.RegisterResultDto
                .builder()
                .restaurantId(savedRestaurant.getId())
                .createdAt(LocalDateTime.now()) // (실제로는 Entity의 @CreatedAt 사용 권장)
                .build();

        // 3. ApiResponse로 감싸서 CREATED(201) 응답 반환
        return ApiResponse.onSuccess(GeneralSuccessCode._CREATED, responseDto);
    }
}
