package com.acoreweb.acoreapi.dto;

import lombok.Data;
import java.util.Set;

@Data
public class BlogRequest {

    private String title;
    private String description;
    private String content;
    private String authorName;

    // FIXED — string allow karega "5 min"
    private String readTime;

    private Set<String> tags;
}
