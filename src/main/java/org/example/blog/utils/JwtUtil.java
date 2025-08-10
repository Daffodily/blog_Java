package org.example.blog.utils;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key key;
    private final long expireSeconds;
    private  final String issuer;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expire-seconds}") long expireSeconds,
                   @Value("${jwt.issuer}") String issuer) {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expireSeconds = expireSeconds;
        this.issuer = issuer;
    }

    public  String generateToken(String phone, Long userId, String userName) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(phone)
                .issuer(issuer)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expireSeconds * 1000))
                .claim("userId", userId)
                .claim("userName", userName)
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parseToken(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token);
    }

}
