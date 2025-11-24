package com.example.umc9th.Chapter4.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MyReviewDto {

    private final String nickName;
    private final int rating;
    private final String reviewContent;
    private final LocalDate createdDate;

    // 💡 QueryDSL에서 DTO로 바로 조회하기 위한 생성자
    @QueryProjection
    public MyReviewDto(String nickName, int rating, String reviewContent, LocalDate createdDate) {
        this.nickName = nickName;
        this.rating = rating;
        this.reviewContent = reviewContent;
        this.createdDate = createdDate;
    }
}