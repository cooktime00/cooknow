package com.side.cooknow.global.config.auth.filter;

import com.side.cooknow.global.config.auth.OauthAuthenticationToken;
import com.side.cooknow.global.model.security.RefreshToken;
import com.side.cooknow.global.service.RefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Log4j2
@RequiredArgsConstructor
public class RefreshFilter extends TokenFilter {
    private final RefreshTokenService refreshTokenService;

    @Override
    protected boolean authenticateToken(String authToken, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("Refresh Filter");
        if (refreshTokenService.existsByToken(authToken)) {
            log.info("Refresh token verified");
            RefreshToken refreshToken = refreshTokenService.findByToken(authToken);
            Long userId = refreshToken.getUserId();
            setAuthenticationAndContinueChain(new OauthAuthenticationToken(userId, "", Collections.emptyList()), request, response, filterChain);
            return true;
        }
        return false;
    }
}
