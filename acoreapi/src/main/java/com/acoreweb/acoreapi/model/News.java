package com.acoreweb.acoreapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "news")
@Data
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(length = 5000)
    private String content;

    // Extra fields jo list me dikh rahe the
    private String authorName;
    private LocalDate publishDate;
    private int readTime; // e.g., 3 min read
    private String category; // e.g., "Awards", "Company News"

    @PrePersist
    protected void onCreate() {
        this.publishDate = LocalDate.now();
    }
}