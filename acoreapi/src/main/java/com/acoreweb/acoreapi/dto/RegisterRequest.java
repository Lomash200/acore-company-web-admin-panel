package com.acoreweb.acoreapi.dto;

public class RegisterRequest {
    private String username; // Ya email
    private String password;

    // Getters and Setters...

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
}