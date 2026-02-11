package com.acoreweb.acoreapi.controller;

import com.acoreweb.acoreapi.dto.ContactInquiryDto;
import com.acoreweb.acoreapi.service.ContactInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/inquiries")  //admin
@CrossOrigin(origins = "http://localhost:5174") // Sirf tere React Admin ko allow karega
public class AdminContactController {

    @Autowired
    private ContactInquiryService contactInquiryService;

    // Sari inquiries get karne ke liye (filtering ke sath)
    // Example: GET /api/admin/inquiries
    // Example: GET /api/admin/inquiries?status=NEW
    @GetMapping
    public ResponseEntity<List<ContactInquiryDto>> getAllInquiries(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type) {

        List<ContactInquiryDto> inquiries;

        if (status != null) {
            inquiries = contactInquiryService.getInquiriesByStatus(status);
        } else if (type != null) {
            inquiries = contactInquiryService.getInquiriesByType(type);
        } else {
            inquiries = contactInquiryService.getAllInquiries();
        }

        return ResponseEntity.ok(inquiries);
    }

    // Ek single inquiry get karne ke liye
    // Example: GET /api/admin/inquiries/1
    @GetMapping("/{id}")
    public ResponseEntity<ContactInquiryDto> getInquiryById(@PathVariable Long id) {
        ContactInquiryDto inquiry = contactInquiryService.getInquiryById(id);
        return ResponseEntity.ok(inquiry);
    }

    // Status update karne ke liye
    // Example: PUT /api/admin/inquiries/1/status?newStatus=READ
    @PutMapping("/{id}/status")
    public ResponseEntity<ContactInquiryDto> updateStatus(
            @PathVariable Long id,
            @RequestParam String newStatus) {

        ContactInquiryDto updatedInquiry = contactInquiryService.updateInquiryStatus(id, newStatus);
        return ResponseEntity.ok(updatedInquiry);
    }

    // Inquiry delete karne ke liye
    // Example: DELETE /api/admin/inquiries/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInquiry(@PathVariable Long id) {
        contactInquiryService.deleteInquiry(id);
        return ResponseEntity.noContent().build();
    }
}