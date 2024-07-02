package com.side.cooktime.global.config.auth;

import com.side.cooktime.global.FirebaseService;
import com.side.cooktime.global.model.security.RefreshToken;
import com.side.cooktime.global.security.JwtTokenService;
import com.side.cooktime.global.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Log4j2
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final FirebaseService firebaseService;
    private final RefreshTokenService refreshTokenService;
    private static final int TOKEN_INDEX = 7;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String authToken = header.substring(TOKEN_INDEX);

            if (authenticateWithFirebase(authToken, request, response, filterChain) ||
                    authenticateWithRefreshToken(authToken, request, response, filterChain) ||
                    authenticateWithJwtToken(authToken, request, response, filterChain)) {
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean authenticateWithFirebase(String authToken, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (firebaseService.verifyToken(authToken)) {
            log.info("Firebase token verified");
            String email = firebaseService.getUserEmail();
            FirebaseAuthenticationToken authentication = new FirebaseAuthenticationToken(email, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }

    private boolean authenticateWithRefreshToken(String authToken, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (refreshTokenService.existsByToken(authToken)) {
            log.info("Refresh token verified");
            RefreshToken refreshToken = refreshTokenService.findByToken(authToken);
            Long userId = refreshToken.getUserId();
            OauthAuthenticationToken authentication = new OauthAuthenticationToken(userId, "", Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
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
            OauthAuthenticationToken authentication = new OauthAuthenticationToken(userId, email, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return true;

        }
        return false;
    }
}