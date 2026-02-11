package com.acoreweb.acoreapi.service;

import com.acoreweb.acoreapi.dto.ContactInquiryDto;
import com.acoreweb.acoreapi.dto.ContactInquiryRequest;

import java.util.List;

public interface ContactInquiryService {

    // Public form se nayi inquiry create karna
    ContactInquiryDto createInquiry(ContactInquiryRequest request);

    // Admin ke liye sari inquiries get karna
    List<ContactInquiryDto> getAllInquiries();

    // Admin ke liye status ke basis pe filter karna
    List<ContactInquiryDto> getInquiriesByStatus(String status);

    // Admin ke liye type ke basis pe filter karna
    List<ContactInquiryDto> getInquiriesByType(String type);

    // Admin ke liye status update karna (e.g., "NEW" se "READ")
    ContactInquiryDto updateInquiryStatus(Long id, String status);

    // Admin ke liye inquiry delete karna
    void deleteInquiry(Long id);

    // Admin ke liye ek single inquiry get karna
    ContactInquiryDto getInquiryById(Long id);
}