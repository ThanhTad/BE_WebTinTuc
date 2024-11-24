package com.sport.news.service;

import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;
    
    @Value("${app.jwtRefreshExpirationMs}")
    private int jwtRefreshExpirationMs;

    public SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Authentication authentication){
        return Jwts.builder()
                .subject(authentication.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(Authentication authentication){
        return Jwts.builder()
                .subject(authentication.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtRefreshExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractToken(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        return Optional.ofNullable(headerAuth)
                .filter(auth -> auth.startsWith("Bearer "))
                .map(auth -> auth.substring("Bearer ".length()))
                .orElse(null);
    }

    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String authtToken){
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(authtToken);
            return true;
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (JwtException e){
            throw new IllegalArgumentException("Invalid token", e);
        }
    }

    public Authentication getAuthentication(String token, UserDetails userDetails){
        if (userDetails == null || userDetails.getUsername() == null || userDetails.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Invalid UserDetails");
        }
        if (validateToken(token) && 
            userDetails.getUsername().equals(extractUsername(token))) {
            return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
            );
        }
        throw new IllegalArgumentException("Invalid token");
    }

}
