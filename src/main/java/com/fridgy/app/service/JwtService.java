package com.fridgy.app.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY = "my_fridgy_app_key_no_one_should_see_it";

    // Creates digital signature (byte array)
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // ideally the key is something you own, will be hashed
    }

    public String generateToken(Long userId) {
        // import Jwts library and use it to generate a JWT token
        String token = Jwts.builder()
                .subject(userId.toString()) // subject is the user id
                .issuedAt(new Date(System.currentTimeMillis())) // issued at time of login
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours
                .signWith(getSigningKey())
                .compact();
        return token;
    }

    // gets the user id from the token
    public Long getUserId(String token) {
        return Long.valueOf(extractAllClaims(token).getSubject());
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // jwt.io to see what the token has in it
    private Date extractExpiration(String token) {
        // extract the claim for expiration
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser() // parse the token
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
