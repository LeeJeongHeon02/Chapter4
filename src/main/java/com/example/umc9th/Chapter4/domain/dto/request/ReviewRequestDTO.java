package com.example.umc9th.Chapter4.domain.dto.request;

import lombok.Getter;

public class ReviewRequestDTO {

    @Getter
    public static class WriteDto {
        private Long memberId;
        private Long restaurantId;
        private String content;
        private Integer rating;
    }
}
