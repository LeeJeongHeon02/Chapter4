package com.example.umc9th.Chapter4.controller;

import com.example.umc9th.Chapter4.domain.dto.MyReviewDto;
import com.example.umc9th.Chapter4.domain.dto.request.ReviewRequestDTO;
import com.example.umc9th.Chapter4.domain.dto.response.ReviewResponseDTO;
import com.example.umc9th.Chapter4.domain.review.Review;
import com.example.umc9th.Chapter4.global.apiPayload.ApiResponse;
import com.example.umc9th.Chapter4.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc9th.Chapter4.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews") // API 공통 경로
public class ReviewController {

    private final ReviewService reviewService;

    // 예: GET /reviews/my/1?restaurantId=10&rating=5&page=0&size=10
    @GetMapping("/my/{memberId}")
    public ResponseEntity<Page<MyReviewDto>> getMyReviews(
            @PathVariable(value = "memberId") Long memberId, // (보안 적용 전이므로 PathVariable로 받습니다)

            // 💡 필터링 파라미터 (필수 아님)
            @RequestParam(value = "restaurantId", required = false) Long restaurantId,
            @RequestParam(value = "rating", required = false) Integer rating,

            // 💡 페이징 파라미터 (기본값: 0번 페이지, 10개씩)
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<MyReviewDto> reviewPage = reviewService.getMyReviews(memberId, restaurantId, rating, pageable);
        return ResponseEntity.ok(reviewPage);
    }

    @PostMapping("/")
    public ApiResponse<ReviewResponseDTO.WriteResultDto> writeReview(@RequestBody ReviewRequestDTO.WriteDto request) {

        Review savedReview = reviewService.writeReview(request);

        ReviewResponseDTO.WriteResultDto resultDto = ReviewResponseDTO.WriteResultDto.builder()
                .reviewId(savedReview.getId())
                .createdAt(LocalDateTime.now())
                .build();

        return ApiResponse.onSuccess(GeneralSuccessCode._CREATED, resultDto);
    }
}