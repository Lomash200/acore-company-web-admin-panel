package com.acoreweb.acoreapi.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ContactInquiryDto {

    // Isme sab fields hain jo admin ko dikhengi
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String subject;
    private String message;
    private String status;
    private String type;
    private LocalDateTime receivedAt;
}