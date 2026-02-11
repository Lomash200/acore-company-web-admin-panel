package com.acoreweb.acoreapi.dto;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String email; // User apna email (jo 'username' hai) yahan daalega
}