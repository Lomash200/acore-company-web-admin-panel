package com.acoreweb.acoreapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Tag name unique hona chahiye
    private String name;

    // Yeh Spring ko batata hai ki 'blogs' field (Blog class me)
    // is relationship ko manage kar raha hai.
    @ManyToMany(mappedBy = "tags")
    private Set<Blog> blogs = new HashSet<>();

    // Helper constructor taaki naam se tag bana sakein
    public Tag(String name) {
        this.name = name;
    }
}