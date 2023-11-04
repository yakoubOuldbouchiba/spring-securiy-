package com.example.demo.jwt;


import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;



@Component
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfiguration {
    
    private String secret;
    private String prefix;
    
    private Integer tokenExperationAfterDays;
    
    public JwtConfiguration() {

    }
    
    public String getSecret() {
        return secret;
    }
    
    public void setSecret(String secret) {
        this.secret = secret;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    public Integer getTokenExperationAfterDays() {
        return tokenExperationAfterDays;
    }
    
    public void setTokenExperationAfterDays(Integer tokenExperationAfterDays) {
        this.tokenExperationAfterDays = tokenExperationAfterDays;
    }
    
    

    
    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
