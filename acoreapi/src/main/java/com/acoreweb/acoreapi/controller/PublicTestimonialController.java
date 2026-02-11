package com.acoreweb.acoreapi.controller;

import com.acoreweb.acoreapi.dto.TestimonialDto;
import com.acoreweb.acoreapi.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/testimonials") // URL 'public' hai
public class PublicTestimonialController {

    @Autowired
    private TestimonialService testimonialService; // Wahi service reuse kar rahe hain

    // ----- Public: Sare Testimonials Get Karna (List) -----
    @GetMapping
    public ResponseEntity<List<TestimonialDto>> getAllTestimonials() {
        List<TestimonialDto> testimonials = testimonialService.getAllTestimonials();
        return ResponseEntity.ok(testimonials);
    }
}