package com.example.umc9th.Chapter4.controller;

import com.example.umc9th.Chapter4.anotation.CheckPage;
import com.example.umc9th.Chapter4.converter.MissionConverter;
import com.example.umc9th.Chapter4.domain.dto.request.MissionRequestDTO;
import com.example.umc9th.Chapter4.domain.dto.request.RestaurantRequestDTO;
import com.example.umc9th.Chapter4.domain.dto.response.MissionResponseDTO;
import com.example.umc9th.Chapter4.domain.dto.response.RestaurantResponseDTO;
import com.example.umc9th.Chapter4.domain.mission.Mission;
import com.example.umc9th.Chapter4.domain.restaurant.Restaurant;
import com.example.umc9th.Chapter4.global.apiPayload.ApiResponse;
import com.example.umc9th.Chapter4.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc9th.Chapter4.service.MissionService;
import com.example.umc9th.Chapter4.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final MissionService missionService;

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

    @PostMapping("/{restaurantId}/missions")
    public ApiResponse<MissionResponseDTO.MissionAddResultDto> addMission(
            @PathVariable() Long restaurantId,
            @RequestBody MissionRequestDTO.MissionAddDto request)
    {
        // 1. Service 호출
        Mission savedMission = missionService.addMissionToRestaurant(restaurantId, request);

        // 2. Entity -> Response DTO 변환
        MissionResponseDTO.MissionAddResultDto responseDto = MissionResponseDTO.MissionAddResultDto.builder()
                .missionId(savedMission.getId())
                .createdAt(LocalDateTime.now()) // (실제로는 Entity의 @CreatedAt 필드 사용 권장)
                .build();

        // 3. 'CREATED(201)' 응답 반환
        return ApiResponse.onSuccess(GeneralSuccessCode._CREATED, responseDto);
    }

    @GetMapping("/{restaurantId}/missions")
    @Operation(summary = "특정 가게의 미션 목록 조회 API", description = "특정 가게의 미션들의 목록을 조회하는 API이며, 페이징을 포함합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH003", description = "access 토큰을 주세요!"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH004", description = "access 토큰 만료"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUTH006", description = "access 토큰 모양이 이상함"),
    })
    @Parameters({
            @Parameter(name = "restaurantId", description = "가게 아이디, Path Variable 입니다."),
            @Parameter(name = "page", description = "페이지 번호, 1 이상입니다."),
    })
    public ApiResponse<MissionResponseDTO.MissionListDTO> getMissions(
            @PathVariable(name = "restaurantId") Long restaurantId,
            @CheckPage @RequestParam(name = "page", defaultValue = "1") Integer page) {
        Page<Mission> missionPage = missionService.getMissions(restaurantId, page - 1);
        return ApiResponse.onSuccess(MissionConverter.toMissionListDTO(missionPage));
    }
}
