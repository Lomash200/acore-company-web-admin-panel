package com.acoreweb.acoreapi.controller;

import com.acoreweb.acoreapi.dto.TestimonialDto;
import com.acoreweb.acoreapi.dto.TestimonialRequest;
import com.acoreweb.acoreapi.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/testimonials")
@CrossOrigin(origins = "http://localhost:5174") // Admin dashboard ko allow karega
public class TestimonialController {

    @Autowired
    private TestimonialService testimonialService;

    // ----- Naya Testimonial Create Karna (POST) -----
    @PostMapping
    public ResponseEntity<TestimonialDto> createTestimonial(@RequestBody TestimonialRequest request) {
        TestimonialDto createdTestimonial = testimonialService.createTestimonial(request);
        return new ResponseEntity<>(createdTestimonial, HttpStatus.CREATED);
    }

    // ----- Sare Testimonials Get Karna (GET) -----
    @GetMapping
    public ResponseEntity<List<TestimonialDto>> getAllTestimonials() {
        List<TestimonialDto> testimonials = testimonialService.getAllTestimonials();
        return ResponseEntity.ok(testimonials);
    }

    // ----- Ek Testimonial Get Karna (GET) -----
    @GetMapping("/{id}")
    public ResponseEntity<TestimonialDto> getTestimonialById(@PathVariable Long id) {
        TestimonialDto testimonial = testimonialService.getTestimonialById(id);
        return ResponseEntity.ok(testimonial);
    }

    // ----- Testimonial Update Karna (PUT) -----
    @PutMapping("/{id}")
    public ResponseEntity<TestimonialDto> updateTestimonial(
            @PathVariable Long id,
            @RequestBody TestimonialRequest request) {

        TestimonialDto updatedTestimonial = testimonialService.updateTestimonial(id, request);
        return ResponseEntity.ok(updatedTestimonial);
    }

    // ----- Testimonial Delete Karna (DELETE) -----
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable Long id) {
        testimonialService.deleteTestimonial(id);
        return ResponseEntity.noContent().build();
    }
}