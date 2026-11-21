package com.uni.expense_tracker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey; // <-- Changed from java.security.Key
import java.util.Date;

@Component
public class JwtUtil {

    // Must be SecretKey for HS256
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final int JWT_EXPIRATION_MS = 86400000; // 24 hours

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                .signWith(key)
                .compact();
    }

    public String extractEmail(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key) // Now accepts SecretKey
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }
}