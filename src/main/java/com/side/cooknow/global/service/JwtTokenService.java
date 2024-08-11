package com.side.cooknow.global.service;

import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.global.exception.OauthErrorCode;
import com.side.cooknow.global.exception.OauthException;
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
    private final static String ACCESS_TOKEN = "Access Token";

    @PostConstruct
    private void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createAccessToken(final User user) {
        return createToken(user);
    }

    public String refreshAccessToken(final User user) {
        return createToken(user);
    }

    public Claims parseClaims(String token) {
        try {
            JwtParser jwtParser = Jwts.parser().verifyWith(key).build();
            return jwtParser.parseSignedClaims(token).getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            logJwtException(e);
            return null;
        }
    }

    public void verifyAccessToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parse(token);
        } catch (JwtException | IllegalArgumentException e) {
            logJwtException(e);
        }
    }

    private String createToken(final User user) {
        return Jwts.builder()
                .header().type("JWT").and()
                .claims()
                .issuer("CookNow")
                .subject(ACCESS_TOKEN)
                .audience().add(user.getEmailValue()).and()
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .add("userId", user.getId())
                .add("userEmail", user.getEmailValue()).and()
                .signWith(key)
                .compact();
    }

    private void logJwtException(Exception e) {
        if (e instanceof SecurityException || e instanceof MalformedJwtException) {
            log.info("Invalid JWT Token", e);
            throw new OauthException(OauthErrorCode.INVALID_ACCESS_TOKEN);
        } else if (e instanceof ExpiredJwtException) {
            log.info("Expired JWT Token", e);
            throw new OauthException(OauthErrorCode.EXPIRED_ACCESS_TOKEN);
        } else if (e instanceof UnsupportedJwtException) {
            log.info("Unsupported JWT Token", e);
            throw new OauthException(OauthErrorCode.UNSUPPORTED_ALGORITHM);
        } else if (e instanceof IllegalArgumentException) {
            log.info("JWT claims string is empty.", e);
            throw new OauthException(OauthErrorCode.INVALID_CLAIMS);
        } else {
            log.info("Unexpected JWT Token error", e);
            throw new OauthException(OauthErrorCode.UNKNOWN_ACCESS_TOKEN);
        }
    }
}
