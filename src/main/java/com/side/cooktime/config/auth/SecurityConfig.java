package com.side.cooktime.config.auth;

import com.side.cooktime.domain.member.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService;

    public SecurityConfig(CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService) {
        this.customOAuth2AuthorizedClientService = customOAuth2AuthorizedClientService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                    .requestMatchers("/login", "/logout", "/css/**", "/", "/img/**", "/download/**", "/static/**", "/h2-console/**").permitAll()
                    .requestMatchers("/oauth2/**").permitAll()
                    .requestMatchers("/user/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated()
                ).oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/user/main")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2AuthorizedClientService)
                        )
                        .failureUrl("/login?error=true")
                        .permitAll()
                ).csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> authorities;
    }

}
