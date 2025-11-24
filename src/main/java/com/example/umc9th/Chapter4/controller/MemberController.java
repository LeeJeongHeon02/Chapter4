package com.example.umc9th.Chapter4.controller;


import com.example.umc9th.Chapter4.anotation.CheckPage;
import com.example.umc9th.Chapter4.converter.MissionConverter;
import com.example.umc9th.Chapter4.domain.dto.MissionDetailsDto;
import com.example.umc9th.Chapter4.domain.dto.request.MemberRequestDTO;
import com.example.umc9th.Chapter4.domain.dto.response.MemberResponseDTO;
import com.example.umc9th.Chapter4.domain.dto.response.MissionResponseDTO;
import com.example.umc9th.Chapter4.domain.member.Member;
import com.example.umc9th.Chapter4.global.apiPayload.ApiResponse;
import com.example.umc9th.Chapter4.global.apiPayload.code.GeneralSuccessCode;
import com.example.umc9th.Chapter4.global.apiPayload.code.MemberSuccessCode;
import com.example.umc9th.Chapter4.service.MemberService;
import com.example.umc9th.Chapter4.service.MissionService;
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
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MissionService missionService;

    @PostMapping("/signup")
    public ApiResponse<MemberResponseDTO.SignUpResultDto> signUp(@RequestBody MemberRequestDTO.SignUpDto request) {
        Member savedMember = memberService.signUp(request);

        // Entity -> Response DTO 변환
        MemberResponseDTO.SignUpResultDto responseDto = MemberResponseDTO.SignUpResultDto
                .builder()
                .memberId(savedMember.getId())
                .createdAt(LocalDateTime.now()) // (주의: 실제로는 Entity의 @CreatedAt 사용)
                .build();

        // 💡 CREATED(201) 코드와 함께 응답
        return ApiResponse.onSuccess(GeneralSuccessCode._CREATED, responseDto);
    }

    @PostMapping("/signup2")
    public ApiResponse<MemberResponseDTO.SignUpResultDto> signUp2(@RequestBody MemberRequestDTO.SignUpDto dto) {
        return ApiResponse.onSuccess(MemberSuccessCode.FOUND, memberService.signUp2(dto));
    }

    @GetMapping("/{memberId}/missions/in-progress")
    @Operation(summary = "내가 진행 중인 미션 목록 조회 API", description = "내가 진행 중인 미션들의 목록을 조회하는 API이며, 페이징을 포함합니다.")
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
    public ApiResponse<MissionResponseDTO.InProgressMissionListDTO> getMyInProgressMissions(
            @PathVariable(name = "memberId") Long memberId,
            @CheckPage @RequestParam(name = "page", defaultValue = "1") Integer page) {
        Page<MissionDetailsDto> missionPage = missionService.findInProgressMissions(memberId, page - 1);
        return ApiResponse.onSuccess(MissionConverter.toInProgressMissionListDTO(missionPage));
    }
}
