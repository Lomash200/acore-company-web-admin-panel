package com.acoreweb.acoreapi.controller;

import com.acoreweb.acoreapi.dto.BlogDto;
import com.acoreweb.acoreapi.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/blogs") // URL 'public' hai
public class PublicBlogController {

    @Autowired
    private BlogService blogService; // Wahi service reuse kar rahe hain

    // ----- Public: Sare Blogs Get Karna (List) -----
    @GetMapping
    public ResponseEntity<List<BlogDto>> getAllBlogs() {
        List<BlogDto> blogs = blogService.getAllBlogs();
        return ResponseEntity.ok(blogs);
    }

    // ----- Public: Ek Blog Get Karna (Read post) -----
    @GetMapping("/{id}")
    public ResponseEntity<BlogDto> getBlogById(@PathVariable Long id) {
        BlogDto blog = blogService.getBlogById(id);
        return ResponseEntity.ok(blog);
    }
}