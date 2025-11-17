package com.example.umc9th.Chapter4.domain.test.Controller;

import com.example.umc9th.Chapter4.domain.test.convertor.TestConverter;
import com.example.umc9th.Chapter4.domain.test.dto.testResDto;
import com.example.umc9th.Chapter4.domain.test.service.command.TestQueryService;
import com.example.umc9th.Chapter4.global.apiPayload.ApiResponse;
import com.example.umc9th.Chapter4.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/temp")
public class TestController {

    private final TestQueryService testQueryService;

    @GetMapping("/test")
    public ApiResponse<testResDto.Testing> test() throws Exception {
        // 응답 코드 정의
        GeneralSuccessCode code = GeneralSuccessCode._OK;

        return ApiResponse.onSuccess(
                code,
                TestConverter.toTestingDTO("This is Test!")
        );
    }

    @GetMapping("/exception")
    public ApiResponse<testResDto.Exception> exception(
            @RequestParam("flag") Long flag
    ) {
        testQueryService.checkFlag(flag);

        // 응답 코드 정의
        GeneralSuccessCode code = GeneralSuccessCode._OK;
        return ApiResponse.onSuccess(code, TestConverter.toExceptionDTO("This is Test!"));

    }
}
