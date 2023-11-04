package com.example.demo.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JwtUserAndPasswordAuthenficationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    
    private final JwtConfiguration jwtConfiguration;
    
    private final JwtSecretKey  jwtSecretKey;
    
    @Autowired
    public JwtUserAndPasswordAuthenficationFilter(AuthenticationManager authenticationManager, JwtConfiguration jwtConfiguration, JwtSecretKey jwtSecretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfiguration = jwtConfiguration;
        this.jwtSecretKey = jwtSecretKey;
    }
    
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPasswordAuthenficationRequest user = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenficationRequest.class);
            
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword()
            );
            Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String jwt= Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities" ,authResult.getAuthorities())
                .issuedAt(new Date())
                .expiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.getTokenExperationAfterDays())))
                .signWith(jwtSecretKey.getSecretKey())
                .compact();
        response.addHeader(jwtConfiguration.getPrefix() , jwtConfiguration.getPrefix()+jwt);
    }
}
