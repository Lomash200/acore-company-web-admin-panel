package com.acoreweb.acoreapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "blogs")
@Data
@NoArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(length = 10000)
    private String content;

    private String authorName;

    private String imageUrl;

    private LocalDate publishDate;

    @Column
    private String readTime;

    // ----- Many-to-Many Relationship -----
    // YAHAN SE 'cascade = CascadeType.ALL' HATA DIYA HAI
    @ManyToMany
    @JoinTable(
            name = "blog_tags",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    // Helper method: Blog me naya tag add karne ke liye
    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getBlogs().add(this);
    }
}