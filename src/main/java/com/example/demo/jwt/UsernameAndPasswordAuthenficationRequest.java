package com.example.demo.jwt;

import java.util.Objects;

public class UsernameAndPasswordAuthenficationRequest
{
    
    private String username;
    private String password;
    
    
    public UsernameAndPasswordAuthenficationRequest() {
    }
    
    public UsernameAndPasswordAuthenficationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsernameAndPasswordAuthenficationRequest that = (UsernameAndPasswordAuthenficationRequest) o;
        return Objects.equals(username, that.username);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
