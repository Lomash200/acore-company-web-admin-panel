package com.acoreweb.acoreapi.dto;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String email;
    private String otp; // 6-digit OTP
}
