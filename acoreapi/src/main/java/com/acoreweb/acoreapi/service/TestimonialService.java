package com.acoreweb.acoreapi.service;

import com.acoreweb.acoreapi.dto.TestimonialDto;
import com.acoreweb.acoreapi.dto.TestimonialRequest;

import java.util.List;

public interface TestimonialService {

    TestimonialDto createTestimonial(TestimonialRequest request);

    TestimonialDto updateTestimonial(Long id, TestimonialRequest request);

    List<TestimonialDto> getAllTestimonials();

    TestimonialDto getTestimonialById(Long id);

    void deleteTestimonial(Long id);
}