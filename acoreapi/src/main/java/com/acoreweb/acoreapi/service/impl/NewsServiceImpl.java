package com.acoreweb.acoreapi.service.impl;

import com.acoreweb.acoreapi.dto.NewsDto;
import com.acoreweb.acoreapi.dto.NewsRequest;
import com.acoreweb.acoreapi.model.News;
import com.acoreweb.acoreapi.repository.NewsRepository;
import com.acoreweb.acoreapi.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    // ----- Helper: Model ko DTO me convert karna -----
    private NewsDto mapToDto(News news) {
        NewsDto dto = new NewsDto();
        dto.setId(news.getId());
        dto.setTitle(news.getTitle());
        dto.setDescription(news.getDescription());
        dto.setContent(news.getContent());
        dto.setAuthorName(news.getAuthorName());
        dto.setPublishDate(news.getPublishDate());
        dto.setReadTime(news.getReadTime());
        dto.setCategory(news.getCategory());
        return dto;
    }

    // ----- Helper: Request DTO ko Model me convert karna -----
    private void updateModelFromRequest(News news, NewsRequest request) {
        news.setTitle(request.getTitle());
        news.setDescription(request.getDescription());
        news.setContent(request.getContent());
        news.setAuthorName(request.getAuthorName());
        news.setReadTime(request.getReadTime());
        news.setCategory(request.getCategory());
        // publishDate automatic @PrePersist se set ho jayega create ke time
    }

    // ----- Service Methods -----

    @Override
    public NewsDto createNews(NewsRequest request) {
        News news = new News();
        updateModelFromRequest(news, request); // Helper use kiya

        News savedNews = newsRepository.save(news);
        return mapToDto(savedNews);
    }

    @Override
    public List<NewsDto> getAllNews() {
        // Nayi news pehle dikhane ke liye
        return newsRepository.findAllByOrderByPublishDateDesc().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NewsDto getNewsById(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
        return mapToDto(news);
    }

    @Override
    public NewsDto updateNews(Long id, NewsRequest request) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));

        updateModelFromRequest(news, request); // Helper use kiya

        News updatedNews = newsRepository.save(news);
        return mapToDto(updatedNews);
    }

    @Override
    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new RuntimeException("News not found with id: " + id);
        }
        newsRepository.deleteById(id);
    }
}