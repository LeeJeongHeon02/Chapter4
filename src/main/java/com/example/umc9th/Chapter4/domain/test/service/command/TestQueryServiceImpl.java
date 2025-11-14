package com.example.umc9th.Chapter4.domain.test.service.command;

import com.example.umc9th.Chapter4.domain.test.exception.TestException;
import com.example.umc9th.Chapter4.domain.test.exception.code.TestErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestQueryServiceImpl implements TestQueryService {

    @Override
    public void checkFlag(Long flag){
        if (flag == 1) {
            throw new TestException(TestErrorCode.TEST_EXCEPTION);
        }
    }
}
