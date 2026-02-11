package com.acoreweb.acoreapi.repository;

import com.acoreweb.acoreapi.model.ChatQuery;
import org.springframework.data.jpa.repository.JpaRepository; // JPA Repository
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatQuery, Long> {
    // ID type Long hai
}