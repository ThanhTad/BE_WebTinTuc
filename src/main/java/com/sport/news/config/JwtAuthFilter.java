package com.sport.news.config;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sport.news.service.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        String path = request.getServletPath();
        if(path.startsWith("/api/auth") || (path.startsWith("/api/users") && request.getMethod().equalsIgnoreCase("POST"))){
            filterChain.doFilter(request, response);
            return;
        }
        
        try {
            String token = jwtUtils.extractToken(request);
            if(token != null){
                if(!jwtUtils.validateToken(token)){
                    
                    String refreshToken = extractRFToken(request);
                    if(refreshToken != null && jwtUtils.validateToken(refreshToken)){
                        String username = jwtUtils.extractUsername(refreshToken);
                        var userDetails = userDetailsService.loadUserByUsername(username);

                        Authentication auth = jwtUtils.getAuthentication(refreshToken, userDetails);
                        String newAccessToken = jwtUtils.generateRefreshToken(auth);

                        response.setHeader("Authorization", "Bearer " + newAccessToken);
                    } else {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or exprired token");
                        return;
                    }

                } else {
                    String username = jwtUtils.extractUsername(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                    
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    private String extractRFToken(HttpServletRequest request) throws IOException{
        try (BufferedReader reader = request.getReader()) {
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(requestBody.toString());
            return jsonNode.get("refreshToken").asText();
    
        } catch (JsonProcessingException e) {
            return null; 
        }
    }

}
