package com.acoreweb.acoreapi.service.impl;

import com.acoreweb.acoreapi.dto.ContactInquiryDto;
import com.acoreweb.acoreapi.dto.ContactInquiryRequest;
import com.acoreweb.acoreapi.model.ContactInquiry;
import com.acoreweb.acoreapi.repository.ContactInquiryRepository;
import com.acoreweb.acoreapi.service.ContactInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Spring ko batata hai ki yeh ek Service class hai
public class ContactInquiryServiceImpl implements ContactInquiryService {

    @Autowired // Automatic ContactInquiryRepository ko yahan inject kar dega
    private ContactInquiryRepository inquiryRepository;

    // ----- Helper Method: Model ko DTO me convert karne ke liye -----
    private ContactInquiryDto mapToDto(ContactInquiry inquiry) {
        ContactInquiryDto dto = new ContactInquiryDto();
        dto.setId(inquiry.getId());
        dto.setFullName(inquiry.getFullName());
        dto.setEmail(inquiry.getEmail());
        dto.setPhoneNumber(inquiry.getPhoneNumber());
        dto.setSubject(inquiry.getSubject());
        dto.setMessage(inquiry.getMessage());
        dto.setStatus(inquiry.getStatus());
        dto.setType(inquiry.getType());
        dto.setReceivedAt(inquiry.getReceivedAt());
        return dto;
    }

    // ----- Helper Method: Request DTO ko Model me convert karne ke liye -----
    private ContactInquiry mapRequestToModel(ContactInquiryRequest request) {
        ContactInquiry inquiry = new ContactInquiry();
        inquiry.setFullName(request.getFullName());
        inquiry.setEmail(request.getEmail());
        inquiry.setPhoneNumber(request.getPhoneNumber());
        inquiry.setSubject(request.getSubject());
        inquiry.setMessage(request.getMessage());
        // Note: Status, Type, aur ReceivedAt @PrePersist me automatic set ho jayenge
        return inquiry;
    }

    // ----- Public Method -----
    @Override
    public ContactInquiryDto createInquiry(ContactInquiryRequest request) {
        // 1. Request DTO ko Model me badla
        ContactInquiry inquiryToSave = mapRequestToModel(request);

        // 2. Model ko Repository use karke DB me save kiya
        ContactInquiry savedInquiry = inquiryRepository.save(inquiryToSave);

        // 3. Saved Model ko wapas DTO me badla aur return kar diya
        return mapToDto(savedInquiry);
    }

    // ----- Admin Methods -----
    @Override
    public List<ContactInquiryDto> getAllInquiries() {
        // Nayi inquiries pehle dikhane ke liye
        List<ContactInquiry> inquiries = inquiryRepository.findAllByOrderByReceivedAtDesc();

        // Sari inquiries ko DTO me badal kar list return ki
        return inquiries.stream()
                .map(this::mapToDto) // mapToDto method ko har item pe call karega
                .collect(Collectors.toList());
    }

    @Override
    public List<ContactInquiryDto> getInquiriesByStatus(String status) {
        List<ContactInquiry> inquiries = inquiryRepository.findAllByStatus(status);
        return inquiries.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ContactInquiryDto> getInquiriesByType(String type) {
        List<ContactInquiry> inquiries = inquiryRepository.findAllByType(type);
        return inquiries.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ContactInquiryDto updateInquiryStatus(Long id, String status) {
        // 1. Pehle inquiry ko ID se find kiya
        ContactInquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inquiry not found with id: " + id)); // Error handling

        // 2. Status update kiya
        inquiry.setStatus(status);

        // 3. Updated inquiry ko wapas save kar diya
        ContactInquiry updatedInquiry = inquiryRepository.save(inquiry);

        return mapToDto(updatedInquiry);
    }

    @Override
    public void deleteInquiry(Long id) {
        // Check kiya ki inquiry exist karti hai ya nahi
        if (!inquiryRepository.existsById(id)) {
            throw new RuntimeException("InR-e not found with id: " + id);
        }
        inquiryRepository.deleteById(id);
    }

    @Override
    public ContactInquiryDto getInquiryById(Long id) {
        ContactInquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inquiry not found with id: " + id));
        return mapToDto(inquiry);
    }
}