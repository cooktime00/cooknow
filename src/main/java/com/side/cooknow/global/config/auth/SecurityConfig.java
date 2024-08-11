package com.side.cooknow.global.config.auth;

import com.side.cooknow.global.FirebaseService;
import com.side.cooknow.global.config.auth.filter.AccessFilter;
import com.side.cooknow.global.config.auth.filter.FirebaseFilter;
import com.side.cooknow.global.config.auth.filter.RefreshFilter;
import com.side.cooknow.global.service.JwtTokenService;
import com.side.cooknow.global.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final FirebaseService firebaseService;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;

    @Bean
    @Profile({"local", "qa"})
    public SecurityFilterChain firebaseSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/oauth/sign-in")  // /oauth/sign-in 요청에만 적용
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll())
                .addFilterBefore(new FirebaseFilter(firebaseService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @Profile({"local", "qa"})
    public SecurityFilterChain refreshSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/oauth/refresh") // /oauth/refresh 요청에만 적용
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())
                .addFilterBefore(new RefreshFilter(refreshTokenService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @Profile({"local", "qa"})
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/oauth/sign-in", "/oauth/refresh").permitAll() // 이미 처리된 경로 제외
                        .requestMatchers("/css/**", "/js/**", "/images/**").denyAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new AccessFilter(jwtTokenService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}