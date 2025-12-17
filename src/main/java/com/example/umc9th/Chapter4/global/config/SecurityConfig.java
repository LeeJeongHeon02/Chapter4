package com.example.umc9th.Chapter4.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final String[] allowUris = {
							// Swagger 허용
            "/members/signup2",
//            "/swagger-ui/**",
//            "/swagger-resources/**",
//            "/v3/api-docs/**",
            "/h2-console/**"  // [수정 1] H2 콘솔 접속 경로 허용 목록에 추가
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                // [추가 1] CSRF 보호 비활성화 (H2 Console은 CSRF 토큰을 사용하지 않음)
                .csrf(csrf -> csrf.disable())
                // [추가 2] X-Frame-Options 비활성화 (H2 Console은 iframe을 사용하므로 화면이 깨지지 않도록 허용)
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()) // 또는 .sameOrigin() 도 가능
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(allowUris).permitAll()
                        .requestMatchers("/swagger-ui/index.html").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}
