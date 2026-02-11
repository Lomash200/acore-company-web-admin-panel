package com.acoreweb.acoreapi.controller;

import com.acoreweb.acoreapi.dto.ContactInquiryDto;
import com.acoreweb.acoreapi.dto.ContactInquiryRequest;
import com.acoreweb.acoreapi.service.ContactInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/contact")//user post
@CrossOrigin(origins = "*") // Public hai, toh kahin se bhi call allow karega
public class PublicContactController {

    @Autowired
    private ContactInquiryService contactInquiryService;

    @PostMapping
    public ResponseEntity<ContactInquiryDto> submitInquiry(@RequestBody ContactInquiryRequest request) {
        ContactInquiryDto createdInquiry = contactInquiryService.createInquiry(request);
        return new ResponseEntity<>(createdInquiry, HttpStatus.CREATED);
    }
}