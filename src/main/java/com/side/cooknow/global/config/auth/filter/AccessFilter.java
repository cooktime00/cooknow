package com.side.cooknow.global.config.auth.filter;

import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.global.config.auth.OauthAuthenticationToken;
import com.side.cooknow.global.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Collections;

@Log4j2
@RequiredArgsConstructor
public class AccessFilter extends TokenFilter {
    private final JwtTokenService jwtTokenService;

    @Override
    protected boolean authenticateToken(String authToken, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        log.info("Access token verified");
        jwtTokenService.verifyAccessToken(authToken);
        Claims claims = jwtTokenService.parseClaims(authToken);
        Long userId = claims.get("userId", Long.class);
        String email = claims.get("userEmail", String.class);
        User user = new User(userId, email);
        setAuthenticationAndContinueChain(new OauthAuthenticationToken(user, Collections.emptyList()), request, response, filterChain);
        return true;
    }
}
