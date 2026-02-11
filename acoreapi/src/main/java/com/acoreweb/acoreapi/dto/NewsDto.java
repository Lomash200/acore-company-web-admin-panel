package com.acoreweb.acoreapi.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class NewsDto {

    private Long id;
    private String title;
    private String description;
    private String content;
    private String authorName;
    private LocalDate publishDate;
    private int readTime;
    private String category;
}