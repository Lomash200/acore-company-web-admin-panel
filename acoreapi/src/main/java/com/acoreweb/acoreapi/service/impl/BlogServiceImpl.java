package com.acoreweb.acoreapi.service.impl;

import com.acoreweb.acoreapi.dto.BlogDto;
import com.acoreweb.acoreapi.dto.BlogRequest;
import com.acoreweb.acoreapi.model.Blog;
import com.acoreweb.acoreapi.model.Tag;
import com.acoreweb.acoreapi.repository.BlogRepository;
import com.acoreweb.acoreapi.repository.TagRepository;
import com.acoreweb.acoreapi.service.BlogService;
import com.acoreweb.acoreapi.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Value("${app.upload-url}")
    private String uploadUrl;

    // Helper: mapToDto methods (rest of the file remains clean)
    private BlogDto mapToDto(Blog blog) {
        BlogDto dto = new BlogDto();
        dto.setId(blog.getId());
        dto.setTitle(blog.getTitle());
        dto.setDescription(blog.getDescription());
        dto.setContent(blog.getContent());
        dto.setAuthorName(blog.getAuthorName());
        dto.setReadTime(blog.getReadTime());
        dto.setPublishDate(blog.getPublishDate());

        if (blog.getImageUrl() != null) {
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(uploadUrl + "/")
                    .path(blog.getImageUrl())
                    .toUriString();
            dto.setImageUrl(imageUrl);
        }

        if (blog.getTags() != null) {
            dto.setTags(blog.getTags().stream()
                    .map(Tag::getName)
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    private Set<Tag> processTags(Set<String> tagNames) {
        Set<Tag> tags = new HashSet<>();
        if (tagNames != null) {
            for (String tagName : tagNames) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            return tagRepository.save(new Tag(tagName));
                        });
                tags.add(tag);
            }
        }
        return tags;
    }

    // ----- Service Methods -----

    @Override
    public BlogDto createBlog(BlogRequest blogRequest, MultipartFile imageFile) {
        // 1. Image save karna
        // --- FIX: 3 ARGUMENTS PASS KIYE (Directory aur Name) ---
        String fileName = fileStorageService.storeFile(imageFile, "blogs", blogRequest.getTitle());

        // 2. Tags ko process karna
        Set<Tag> tags = processTags(blogRequest.getTags());

        // 3. Naya Blog object banana
        Blog blog = new Blog();
        blog.setTitle(blogRequest.getTitle());
        blog.setDescription(blogRequest.getDescription());
        blog.setContent(blogRequest.getContent());
        blog.setAuthorName(blogRequest.getAuthorName());
        blog.setReadTime(blogRequest.getReadTime());
        blog.setTags(tags);
        blog.setImageUrl(fileName); // Sirf file ka naam save karna
        blog.setPublishDate(LocalDate.now()); // Aaj ki date

        // 4. Blog ko DB me save karna
        Blog savedBlog = blogRepository.save(blog);

        // 5. DTO return karna
        return mapToDto(savedBlog);
    }

    @Override
    public List<BlogDto> getAllBlogs() {
        return blogRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BlogDto getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));
        return mapToDto(blog);
    }

    @Override
    public void deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));

        // 1. Image file delete karna
        if (blog.getImageUrl() != null) {
            fileStorageService.deleteFile(blog.getImageUrl());
        }

        // 2. Blog delete karna (DB se)
        blogRepository.delete(blog);
    }

    @Override
    public BlogDto updateBlog(Long id, BlogRequest blogRequest, MultipartFile imageFile) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));

        // 1. Details update karna
        blog.setTitle(blogRequest.getTitle());
        blog.setDescription(blogRequest.getDescription());
        blog.setContent(blogRequest.getContent());
        blog.setAuthorName(blogRequest.getAuthorName());
        blog.setReadTime(blogRequest.getReadTime());

        // 2. Tags update karna
        Set<Tag> tags = processTags(blogRequest.getTags());
        blog.setTags(tags);

        // 3. Image update karna (Agar nayi image di hai toh)
        if (imageFile != null && !imageFile.isEmpty()) {
            // Purani image delete karo
            if (blog.getImageUrl() != null) {
                fileStorageService.deleteFile(blog.getImageUrl());
            }
            // Nayi image save karo
            // --- FIX: 3 ARGUMENTS PASS KIYE ---
            String newFileName = fileStorageService.storeFile(imageFile, "blogs", blogRequest.getTitle());
            blog.setImageUrl(newFileName);
        }

        // 4. Update ki hui blog save karo
        Blog updatedBlog = blogRepository.save(blog);
        return mapToDto(updatedBlog);
    }
}