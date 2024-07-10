package com.side.cooknow.global.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import static org.springframework.security.config.Customizer.*;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    @Profile({"local", "qa"})
    public SecurityFilterChain devProtectedSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/oauth/sign-in", "/docs/api.html", "/css/**", "/", "/img/**", "/download/**", "/static/**").permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
//                        requestMatchers("/login", "/logout", "/css/**", "/", "/img/**", "/download/**", "/static/**").permitAll()
//                                .requestMatchers("/login", "/logout", "/css/**", "/", "/img/**", "/download/**", "/static/**", "/h2-console/**").permitAll()
//                                .requestMatchers("/oauth2/**").permitAll()
////                        .requestMatchers("/api/user/**").hasRole(Role.ADMIN.name())
//                                .requestMatchers("/api/hello", "/api/authenticate", "/api/signup", "/error").permitAll()
//                                .requestMatchers("/docs/api.html").permitAll()
//                                .requestMatchers("/api/v1/callback", "/api/v1/google-login", "/favicon.ico").permitAll())
//                .httpBasic(withDefaults())
    }

//    @Bean
//    @Profile({"local", "qa"})
//    public SecurityFilterChain devPublicSecurityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize ->
//                        authorize.anyRequest().permitAll())
//                .build();
////                        requestMatchers("/login", "/logout", "/css/**", "/", "/img/**", "/download/**", "/static/**").permitAll()
////                                .requestMatchers("/login", "/logout", "/css/**", "/", "/img/**", "/download/**", "/static/**", "/h2-console/**").permitAll()
////                                .requestMatchers("/oauth2/**").permitAll()
//////                        .requestMatchers("/api/user/**").hasRole(Role.ADMIN.name())
////                                .requestMatchers("/api/hello", "/api/authenticate", "/api/signup", "/error").permitAll()
////                                .requestMatchers("/docs/api.html").permitAll()
////                                .requestMatchers("/api/v1/callback", "/api/v1/google-login", "/favicon.ico").permitAll())
////                .httpBasic(withDefaults())
//    }

    @Bean
    @Profile("prod")
    public SecurityFilterChain prodSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        return http
                .csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/h2-console/**")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/login", "/logout", "/css/**", "/", "/img/**", "/download/**", "/static/**").authenticated()
                                .requestMatchers("/login", "/logout", "/css/**", "/", "/img/**", "/download/**", "/static/**", "/h2-console/**").permitAll()
                                .requestMatchers("/oauth2/**").permitAll()
//                        .requestMatchers("/api/user/**").hasRole(Role.ADMIN.name())
                                .requestMatchers("/api/hello", "/api/authenticate", "/api/signup", "/error").permitAll()
                                .requestMatchers("/docs/api.html").permitAll()
                                .requestMatchers("/api/v1/callback", "/api/v1/google-login", "/favicon.ico").permitAll())
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
                .build();
    }


//    @Bean
//
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
//                .csrf(AbstractHttpConfigurer::disable)
//
//                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .accessDeniedHandler(jwtAccessDeniedHandler)
//                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                )
//
//                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
//                                .requestMatchers("/login", "/logout", "/css/**", "/", "/img/**", "/download/**", "/static/**", "/h2-console/**").permitAll()
//                                .requestMatchers("/oauth2/**").permitAll()
////                        .requestMatchers("/api/user/**").hasRole(Role.ADMIN.name())
//                                .requestMatchers("/api/authenticate", "/error").permitAll()
//                                .requestMatchers("/docs/api.html").permitAll()
////                                .anyRequest().authenticated() //TODO: ADMIN, USER 접근 api 구분 필요
//                                .anyRequest().permitAll() //TODO: 개발 편의상 임시 해제
//                )
//
//                // 세션을 사용하지 않기 때문에 STATELESS로 설정
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
//                // enable h2-console
//                .headers(headers ->
//                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin
//                        )
//                )
//
//                .apply(new JwtSecurityConfig(tokenProvider));
//        return http.build();
//    }
}
