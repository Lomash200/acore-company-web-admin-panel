package com.acoreweb.acoreapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "chat_queries")
@Getter
@Setter
public class ChatQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // 🔥 IMPORTANT: TEXT column (long queries safe)
    @Column(name = "user_query", columnDefinition = "TEXT")
    private String userQuery;

    // 🔥 IMPORTANT: TEXT column (long bot responses safe)
    @Column(name = "bot_response", columnDefinition = "TEXT")
    private String botResponse;
}
