package com.side.cooknow.global.config.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.side.cooknow.global.exception.*;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public abstract class TokenFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authToken = extractAuthToken(request);
        try {
            if (authToken != null && authenticateToken(authToken, request, response, filterChain)) {
                return;
            }
            filterChain.doFilter(request, response);
        } catch (JwtException | OauthException e) {
            setErrorResponse(response, e);
        }
    }

    private void setErrorResponse(HttpServletResponse response, Exception e) {
        ErrorCode errorCode = extractErrorCode(e);
        ErrorResponseEntity errorResponseEntity = ErrorResponseEntity.of(errorCode);
        response.setStatus(errorResponseEntity.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        try {
            String jsonResponse = objectMapper.writeValueAsString(errorResponseEntity);
            response.getWriter().write(jsonResponse);
        } catch (IOException ioException) {
            ioException.printStackTrace();  // 예외 로깅
        }
    }

    private ErrorCode extractErrorCode(Exception e) {
        if (e instanceof OauthException) {
            return ((OauthException) e).getErrorCode();
        } else {
            return null;
        }
    }


    private String extractAuthToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX_LENGTH);
        }
        return null;
    }

    protected abstract boolean authenticateToken(String authToken, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException;

    protected void setAuthenticationAndContinueChain(Authentication authentication, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
