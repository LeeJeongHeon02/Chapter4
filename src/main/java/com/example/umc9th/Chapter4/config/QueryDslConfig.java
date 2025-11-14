package com.example.umc9th.Chapter4.config; // 본인 프로젝트에 맞게 패키지 경로는 확인하세요.

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {

    // EntityManager를 주입받습니다.
    @PersistenceContext
    private EntityManager entityManager;

    // JPAQueryFactory를 Bean으로 등록합니다.
    @Bean
    public JPAQueryFactory jpaQueryFactory()     {
        return new JPAQueryFactory(entityManager);
    }
}