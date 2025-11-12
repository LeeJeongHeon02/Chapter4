package com.example.umc9th.Chapter4.domain.dto.response;

import lombok.*;

import java.time.LocalDateTime;

public class RestaurantResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterResultDto {
        private Long restaurantId;
        private String restaurantName;
        private String restaurantAddress;
        private LocalDateTime createdAt;


    }
}
