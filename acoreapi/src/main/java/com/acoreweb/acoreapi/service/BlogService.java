package com.acoreweb.acoreapi.service;

import com.acoreweb.acoreapi.dto.BlogDto;
import com.acoreweb.acoreapi.dto.BlogRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogService {

    // Naya blog post create karna (Image file ke sath)
    BlogDto createBlog(BlogRequest blogRequest, MultipartFile imageFile);

    // Blog post update karna (Optional: nayi image file ke sath)
    BlogDto updateBlog(Long id, BlogRequest blogRequest, MultipartFile imageFile);

    // Sare blogs get karna
    List<BlogDto> getAllBlogs();

    // Ek single blog get karna
    BlogDto getBlogById(Long id);

    // Blog delete karna
    void deleteBlog(Long id);
}