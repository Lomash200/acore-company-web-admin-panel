package com.acoreweb.acoreapi.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;       // ✔ required
    private String newPassword; // ✔ required
}
