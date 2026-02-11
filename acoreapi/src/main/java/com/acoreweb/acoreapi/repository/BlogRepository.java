package com.acoreweb.acoreapi.repository;

import com.acoreweb.acoreapi.model.Blog;
import org.springframework.data.jpa.repository.EntityGraph; // Yeh import add karna
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Yeh import add karna

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    // --- YEH NAYA CODE HAI ---
    // Hibernate ko bol rahe hain ki jab bhi 'findAll' call ho,
    // toh "tags" wali property ko bhi sath me fetch kar lena.
    @Override
    @EntityGraph(attributePaths = {"tags"})
    List<Blog> findAll();

    // -------------------------

}