package com.example.demo.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {
    
    private final JwtConfiguration jwtConfiguration;
    private final JwtSecretKey jwtSecretKey;
    
    @Autowired
    public JwtTokenVerifier(JwtConfiguration jwtConfiguration, JwtSecretKey jwtSecretKey) {
        this.jwtConfiguration = jwtConfiguration;
        this.jwtSecretKey = jwtSecretKey;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(jwtConfiguration.getAuthorizationHeader());
        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfiguration.getPrefix())){
            filterChain.doFilter(request , response );
            return;
        }
        
        try{
            String token = authorizationHeader.replace(jwtConfiguration.getPrefix() , "");
         
            Jws<Claims> parsed = Jwts.parser().setSigningKey(jwtSecretKey.getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            Claims body = parsed.getBody();
            String username = body.getSubject();
            var authorities = (List<Map<String, String>>) body.get("authorities");
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream().map(authority->authority.get("authority")).map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toSet());
            
            Authentication authentication =  new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );;
            SecurityContextHolder.getContext().setAuthentication(authentication);
    
        }catch (JwtException exception){
            throw new IllegalStateException("token connet be trusted");
        }
    
        filterChain.doFilter(request ,response);
    }
}
