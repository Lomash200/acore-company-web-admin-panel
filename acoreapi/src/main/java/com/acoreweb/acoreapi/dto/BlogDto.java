package com.acoreweb.acoreapi.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class BlogDto {

    private Long id;
    private String title;
    private String description;
    private String content;
    private String authorName;
    private String imageUrl;
    private LocalDate publishDate;

    // FIXED — string
    private String readTime;

    private Set<String> tags;
}
