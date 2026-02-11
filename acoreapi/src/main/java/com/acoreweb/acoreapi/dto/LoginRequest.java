package com.acoreweb.acoreapi.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username; // Form me "Email or phone number" hai, apan "admin" bhejenge
    private String password;
}