package com.side.cooknow.global.security;

import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.domain.user.service.UserService;
import com.side.cooknow.global.config.auth.AuthenticationFacade;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Log4j2
@RequiredArgsConstructor
@Service
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-expiration}")
    private long accessTokenExpiration;

    private SecretKey key;
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;
    private final static String ACCESS_TOKEN = "Access Token";

    @PostConstruct
    private void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createAccessToken() {
        User user = getAuthenticatedUser();
        return generateToken(user);
    }

    public String refreshAccessToken() {
        User user = getAuthenticatedUserById();
        return generateToken(user);
    }

    public Claims parseClaims(String token) {
        JwtParser jwtParser = Jwts.parser().verifyWith(key).build();
        return jwtParser.parseSignedClaims(token).getPayload();
    }

    public boolean verifyAccessToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logJwtException(e);
        }
        return false;
    }

    private String generateToken(User user) {
        return createToken(user, accessTokenExpiration);
    }

    private User getAuthenticatedUser() {
        String email = authenticationFacade.getAuthenticatedUserEmail();
        return userService.findByEmail(email);
    }

    private User getAuthenticatedUserById() {
        Long userId = authenticationFacade.getAuthenticatedUserId();
        return userService.findByUserId(userId);
    }

    private String createToken(final User user, final Long validityInMilliseconds) {
        return Jwts.builder()
                .header().type("JWT").and()
                .claims()
                .issuer("CookNow")
                .subject(ACCESS_TOKEN)
                .audience().add(user.getEmailValue()).and()
                .expiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .add("userId", user.getId()).and()
                .signWith(key)
                .compact();
    }

    private void logJwtException(Exception e) {
        if (e instanceof SecurityException || e instanceof MalformedJwtException) {
            log.info("Invalid JWT Token", e);
        } else if (e instanceof ExpiredJwtException) {
            log.info("Expired JWT Token", e);
        } else if (e instanceof UnsupportedJwtException) {
            log.info("Unsupported JWT Token", e);
        } else if (e instanceof IllegalArgumentException) {
            log.info("JWT claims string is empty.", e);
        } else {
            log.info("Unexpected JWT Token error", e);
        }
    }
}
