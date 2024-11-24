package com.sport.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport.news.dto.JwtResponse;
import com.sport.news.dto.LoginRequest;
import com.sport.news.dto.RefreshTokenRequest;
import com.sport.news.exception.InvalidRefreshTokenException;
import com.sport.news.service.JwtUtils;
import com.sport.news.service.impl.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody  LoginRequest loginRequest) {
        try {
            log.info("Login attempt for user: {}", loginRequest.getUsername());
    
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
    
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    
            String accessToken = jwtUtils.generateToken(authentication);
            String refreshToken = jwtUtils.generateRefreshToken(authentication);
    
            log.info("User {} logged in successfully.", userDetails.getUsername());
    
            return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
    
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            log.error("Login failed for user: {}. Unexpected error: ", loginRequest.getUsername(), e);
            throw e;
        }
        
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            String rfToken = refreshTokenRequest.getRefreshToken();
            log.info("Received refresh token: {}", rfToken);
    
            if (jwtUtils.validateToken(rfToken)) {
                String username = jwtUtils.extractUsername(rfToken);
                log.info("Extracted username from token: {}", username);
    
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                log.info("Access token refreshed for user: {}", username);
    
                Authentication authentication = jwtUtils.getAuthentication(rfToken, userDetails);
                String newAccessToken = jwtUtils.generateToken(authentication);
    
                return ResponseEntity.ok(new JwtResponse(newAccessToken, rfToken));
            } else {
                log.warn("Invalid refresh token provided");
                throw new InvalidRefreshTokenException("Invalid Refresh Token");
            }
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while refreshing token for user", e);
            throw e;
        }
    }
    
    

}
