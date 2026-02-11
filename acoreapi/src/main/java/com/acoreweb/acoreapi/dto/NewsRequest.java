package com.acoreweb.acoreapi.dto;

import lombok.Data;

@Data
public class NewsRequest {

    private String title;
    private String description;
    private String content;

    // Yeh fields list me dikh rahi thin
    private String authorName;
    private int readTime;
    private String category; // e.g., "Awards"
}