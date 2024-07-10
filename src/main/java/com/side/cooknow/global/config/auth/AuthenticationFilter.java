package com.side.cooknow.global.config.auth;

import com.side.cooknow.global.FirebaseService;
import com.side.cooknow.global.model.security.RefreshToken;
import com.side.cooknow.global.security.JwtTokenService;
import com.side.cooknow.global.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Log4j2
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();

    private final JwtTokenService jwtTokenService;
    private final FirebaseService firebaseService;
    private final RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = extractAuthToken(request);

        if (authToken != null && authenticateToken(authToken, request, response, filterChain)) {
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String extractAuthToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX_LENGTH);
        }
        return null;
    }

    private boolean authenticateToken(String authToken, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        return authenticateWithFirebase(authToken, request, response, filterChain) ||
                authenticateWithRefreshToken(authToken, request, response, filterChain) ||
                authenticateWithJwtToken(authToken, request, response, filterChain);
    }

    private boolean authenticateWithFirebase(String authToken, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (firebaseService.verifyToken(authToken)) {
            log.info("Firebase token verified");
            String email = firebaseService.getUserEmail();
            setAuthenticationAndContinueChain(new FirebaseAuthenticationToken(email, Collections.emptyList()), request, response, filterChain);
            return true;
        }
        return false;
    }

    private boolean authenticateWithRefreshToken(String authToken, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (refreshTokenService.existsByToken(authToken)) {
            log.info("Refresh token verified");
            RefreshToken refreshToken = refreshTokenService.findByToken(authToken);
            Long userId = refreshToken.getUserId();
            setAuthenticationAndContinueChain(new OauthAuthenticationToken(userId, "", Collections.emptyList()), request, response, filterChain);
            return true;
        }
        return false;
    }

    private boolean authenticateWithJwtToken(String authToken, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (jwtTokenService.verifyAccessToken(authToken)) {
            log.info("Access token verified");
            Claims claims = jwtTokenService.parseClaims(authToken);
            Long userId = claims.get("userId", Long.class);
            String email = claims.getSubject();
            setAuthenticationAndContinueChain(new OauthAuthenticationToken(userId, email, Collections.emptyList()), request, response, filterChain);
            return true;
        }
        return false;
    }

    private void setAuthenticationAndContinueChain(Authentication authentication, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}