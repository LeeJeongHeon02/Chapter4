package com.example.umc9th.Chapter4.domain.dto.request;

import lombok.Getter;

public class RestaurantRequestDTO {

    @Getter
    public static class RegisterDto {
        private String restaurantName;
        private String restaurantAddress;
        private Long locationId;

    }
}
