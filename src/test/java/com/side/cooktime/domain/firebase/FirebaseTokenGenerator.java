package com.side.cooktime.domain.firebase;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FirebaseTokenGenerator {
    public static String createTestToken(String userId, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", userId);
        claims.put("email", email);

        return Jwts.builder()
                .claims(claims)
                .subject(userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
                .signWith(SignatureAlgorithm.HS256, "secret") // Use a secret key
                .compact();
    }
}
