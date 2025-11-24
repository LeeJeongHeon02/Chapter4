package com.example.umc9th.Chapter4.controller;

import com.example.umc9th.Chapter4.anotation.CheckPage;
import com.example.umc9th.Chapter4.converter.ReviewConverter;
import com.example.umc9th.Chapter4.domain.dto.MyReviewDto;
import com.example.umc9th.Chapter4.domain.dto.request.ReviewRequestDTO;
import com.example.umc9th.Chapter4.domain.dto.response.ReviewResponseDTO;
import com.example.umc9th.Chapter4.domain.review.Review;
import com.example.umc9th.Chapter4.global.apiPayload.ApiResponse;
import com.example.umc9th.Chapter4.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc9th.Chapter4.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/reviews") // API 공통 경로
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/my/{memberId}")
    @Operation(summary = "내가 작성한 리뷰 목록 조회 API", description = "내가 작성한 리뷰들의 목록을 조회하는 API이며, 페이징을 포함합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함"),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "사용자 아이디, Path Variable 입니다."),
            @Parameter(name = "page", description = "페이지 번호, 1 이상입니다."),
    })
    public ApiResponse<ReviewResponseDTO.MyReviewListDTO> getMyReviews(
            @PathVariable(name = "memberId") Long memberId,
            @CheckPage @RequestParam(name = "page", defaultValue = "1") Integer page) {
        Page<MyReviewDto> reviewPage = reviewService.getMyReviews(memberId, null, null, PageRequest.of(page - 1, 10));
        return ApiResponse.onSuccess(ReviewConverter.toMyReviewListDTO(reviewPage));
    }

    @PostMapping("/")
    public ApiResponse<ReviewResponseDTO.WriteResultDto> writeReview(@RequestBody @Valid ReviewRequestDTO.WriteDto request) {

        Review savedReview = reviewService.writeReview(request);

        ReviewResponseDTO.WriteResultDto resultDto = ReviewResponseDTO.WriteResultDto.builder()
                .reviewId(savedReview.getId())
                .createdAt(LocalDateTime.now())
                .build();

        return ApiResponse.onSuccess(GeneralSuccessCode._CREATED, resultDto);
    }
}