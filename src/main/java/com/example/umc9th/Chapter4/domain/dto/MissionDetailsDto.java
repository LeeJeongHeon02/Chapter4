package com.example.umc9th.Chapter4.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class MissionDetailsDto {

    private final String restaurantName;
    private final String missionContent;
    private final LocalDate missionDeadline;
    private final int successScore;
}
