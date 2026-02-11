package com.acoreweb.acoreapi.dto;

import lombok.Data;

@Data
public class ContactInquiryRequest {

    // Sirf woh fields jo user form me bharega
    private String fullName;
    private String email;
    private String phoneNumber;
    private String subject;
    private String message;
}