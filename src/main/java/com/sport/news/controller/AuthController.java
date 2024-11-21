package com.sport.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sport.news.DTO.JwtResponse;
import com.sport.news.DTO.LoginRequest;
import com.sport.news.DTO.RefreshTokenRequest;
import com.sport.news.service.JwtUtils;
import com.sport.news.service.impl.UserDetailsServiceImpl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/auth")
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
            System.out.println(loginRequest.getUsername() + loginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtUtils.generateToken(authentication);
            String refreshToken = jwtUtils.generateRefreshToken(authentication);

            return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            String rfToken = refreshTokenRequest.getRefreshToken();
            System.out.println("Received refresh token: " + rfToken);
    
            if (jwtUtils.validateToken(rfToken)) {
                String username = jwtUtils.extractUsername(rfToken);
                System.out.println("Extracted username from token: " + username);
    
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("Loaded user details: " + userDetails.getUsername());
    
                Authentication authentication = jwtUtils.getAuthentication(rfToken, userDetails);
                String newAccessToken = jwtUtils.generateToken(authentication);
    
                return ResponseEntity.ok(new JwtResponse(newAccessToken, rfToken));
            } else {
                System.out.println("Invalid Refresh Token");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while refreshing token");
        }
    }
    
    

}
