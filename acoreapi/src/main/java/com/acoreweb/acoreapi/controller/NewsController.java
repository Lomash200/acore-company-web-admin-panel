package com.acoreweb.acoreapi.controller;

import com.acoreweb.acoreapi.dto.NewsDto;
import com.acoreweb.acoreapi.dto.NewsRequest;
import com.acoreweb.acoreapi.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/news")
@CrossOrigin(origins = "http://localhost:5174") // Admin dashboard ko allow karega
public class NewsController {

    @Autowired
    private NewsService newsService;

    // ----- Nayi News Create Karna (POST) -----
    @PostMapping
    public ResponseEntity<NewsDto> createNews(@RequestBody NewsRequest request) {
        NewsDto createdNews = newsService.createNews(request);
        return new ResponseEntity<>(createdNews, HttpStatus.CREATED);
    }

    // ----- Sari News Get Karna (GET) -----
    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNews() {
        List<NewsDto> newsList = newsService.getAllNews();
        return ResponseEntity.ok(newsList);
    }

    // ----- Ek News Get Karna (GET) -----
    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable Long id) {
        NewsDto news = newsService.getNewsById(id);
        return ResponseEntity.ok(news);
    }

    // ----- News Update Karna (PUT) -----
    @PutMapping("/{id}")
    public ResponseEntity<NewsDto> updateNews(
            @PathVariable Long id,
            @RequestBody NewsRequest request) {

        NewsDto updatedNews = newsService.updateNews(id, request);
        return ResponseEntity.ok(updatedNews);
    }

    // ----- News Delete Karna (DELETE) -----
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }
}