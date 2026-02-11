package com.acoreweb.acoreapi.controller;

import com.acoreweb.acoreapi.dto.NewsDto;
import com.acoreweb.acoreapi.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/news") // URL 'public' hai
public class PublicNewsController {

    @Autowired
    private NewsService newsService; // Wahi service reuse kar rahe hain

    // ----- Public: Sari News Get Karna (List) -----
    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNews() {
        List<NewsDto> newsList = newsService.getAllNews();
        return ResponseEntity.ok(newsList);
    }

    // ----- Public: Ek News Get Karna (Details) -----
    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable Long id) {
        NewsDto news = newsService.getNewsById(id);
        return ResponseEntity.ok(news);
    }
}