package com.example.demo.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtSecretKey {
    
    private final JwtConfiguration jwtConfiguration;
    
    public JwtSecretKey(JwtConfiguration configuration) {
        jwtConfiguration   = configuration;
    }
    
    @Bean
    
    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtConfiguration.getSecret().getBytes());
    }
}
