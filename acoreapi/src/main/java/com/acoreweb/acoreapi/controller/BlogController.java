package com.acoreweb.acoreapi.controller;

import com.acoreweb.acoreapi.dto.BlogDto;
import com.acoreweb.acoreapi.dto.BlogRequest;
import com.acoreweb.acoreapi.service.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper; // JSON conversion ke liye
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/blogs")
@CrossOrigin(origins = "http://localhost:5174") // Admin dashboard ko allow karega
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private ObjectMapper objectMapper; // JSON string ko object me badalne ke liye

    // ----- Naya Blog Post Create Karna (POST) -----
    // Yahan apan 'MediaType.MULTIPART_FORM_DATA_VALUE' use kar rahe hain
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BlogDto> createBlog(
            // React se ek 'blogData' naam ka stringified JSON bhejenge
            @RequestParam("blogData") String blogDataString,
            // React se ek 'imageFile' naam ki file bhejenge
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        // 1. JSON string ko wapas BlogRequest object me convert karna
        BlogRequest blogRequest = objectMapper.readValue(blogDataString, BlogRequest.class);

        // 2. Service ko call karke blog create karna
        BlogDto createdBlog = blogService.createBlog(blogRequest, imageFile);
        return new ResponseEntity<>(createdBlog, HttpStatus.CREATED);
    }

    // ----- Sare Blogs Get Karna (GET) -----
    @GetMapping
    public ResponseEntity<List<BlogDto>> getAllBlogs() {
        List<BlogDto> blogs = blogService.getAllBlogs();
        return ResponseEntity.ok(blogs);
    }

    // ----- Ek Blog Get Karna (GET) -----
    @GetMapping("/{id}")
    public ResponseEntity<BlogDto> getBlogById(@PathVariable Long id) {
        BlogDto blog = blogService.getBlogById(id);
        return ResponseEntity.ok(blog);
    }

    // ----- Blog Update Karna (PUT) -----
    // Update me bhi JSON + File aayegi
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BlogDto> updateBlog(
            @PathVariable Long id,
            @RequestParam("blogData") String blogDataString,
            // Image file optional ho sakti hai update ke time
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {

        BlogRequest blogRequest = objectMapper.readValue(blogDataString, BlogRequest.class);
        BlogDto updatedBlog = blogService.updateBlog(id, blogRequest, imageFile);
        return ResponseEntity.ok(updatedBlog);
    }

    // ----- Blog Delete Karna (DELETE) -----
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }
}