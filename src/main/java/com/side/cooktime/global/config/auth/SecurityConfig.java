package com.side.cooktime.global.config.auth;

import com.side.cooktime.global.config.auth.jwt.JwtAccessDeniedHandler;
import com.side.cooktime.global.config.auth.jwt.JwtAuthenticationEntryPoint;
import com.side.cooktime.global.config.auth.jwt.JwtSecurityConfig;
import com.side.cooktime.global.config.auth.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf(AbstractHttpConfigurer::disable)

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers("/login", "/logout", "/css/**", "/", "/img/**", "/download/**", "/static/**", "/h2-console/**").permitAll()
                                .requestMatchers("/oauth2/**").permitAll()
//                        .requestMatchers("/api/user/**").hasRole(Role.ADMIN.name())
                                .requestMatchers("/api/hello", "/api/authenticate", "/api/signup", "/error").permitAll()
                                .requestMatchers("/docs/api.html").permitAll()
                                .requestMatchers("/api/v1/callback", "/api/v1/google-login", "/favicon.ico").permitAll()
//                                .anyRequest().authenticated() //TODO: ADMIN, USER 접근 api 구분 필요
                                .anyRequest().permitAll() //TODO: 개발 편의상 임시 해제
                )

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // enable h2-console
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin
                        )
                )

                .apply(new JwtSecurityConfig(tokenProvider));
        return http.build();
    }
}
