package com.acoreweb.acoreapi.repository;

import com.acoreweb.acoreapi.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    // Nayi news ko pehle dikhane ke liye
    List<News> findAllByOrderByPublishDateDesc();
}