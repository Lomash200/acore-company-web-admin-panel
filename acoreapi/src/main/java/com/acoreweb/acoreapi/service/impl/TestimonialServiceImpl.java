package com.acoreweb.acoreapi.service.impl;

import com.acoreweb.acoreapi.dto.TestimonialDto;
import com.acoreweb.acoreapi.dto.TestimonialRequest;
import com.acoreweb.acoreapi.model.Testimonial;
import com.acoreweb.acoreapi.repository.TestimonialRepository;
import com.acoreweb.acoreapi.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestimonialServiceImpl implements TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepository;

    // ----- Helper: Model ko DTO me convert karna -----
    private TestimonialDto mapToDto(Testimonial testimonial) {
        TestimonialDto dto = new TestimonialDto();
        dto.setId(testimonial.getId());
        dto.setQuote(testimonial.getQuote());
        dto.setPersonName(testimonial.getPersonName());
        dto.setPersonPosition(testimonial.getPersonPosition());
        return dto;
    }

    // ----- Helper: Request DTO ko Model me convert karna -----
    private void updateModelFromRequest(Testimonial testimonial, TestimonialRequest request) {
        testimonial.setQuote(request.getQuote());
        testimonial.setPersonName(request.getPersonName());
        testimonial.setPersonPosition(request.getPersonPosition());
    }

    // ----- Service Methods -----

    @Override
    public TestimonialDto createTestimonial(TestimonialRequest request) {
        Testimonial testimonial = new Testimonial();
        updateModelFromRequest(testimonial, request); // Helper use kiya

        Testimonial savedTestimonial = testimonialRepository.save(testimonial);
        return mapToDto(savedTestimonial);
    }

    @Override
    public List<TestimonialDto> getAllTestimonials() {
        return testimonialRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TestimonialDto getTestimonialById(Long id) {
        Testimonial testimonial = testimonialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Testimonial not found with id: " + id));
        return mapToDto(testimonial);
    }

    @Override
    public TestimonialDto updateTestimonial(Long id, TestimonialRequest request) {
        Testimonial testimonial = testimonialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Testimonial not found with id: " + id));

        updateModelFromRequest(testimonial, request); // Helper use kiya

        Testimonial updatedTestimonial = testimonialRepository.save(testimonial);
        return mapToDto(updatedTestimonial);
    }

    @Override
    public void deleteTestimonial(Long id) {
        if (!testimonialRepository.existsById(id)) {
            throw new RuntimeException("Testimonial not found with id: " + id);
        }
        testimonialRepository.deleteById(id);
    }
}