package com.acoreweb.acoreapi.service;

import com.acoreweb.acoreapi.dto.NewsDto;
import com.acoreweb.acoreapi.dto.NewsRequest;

import java.util.List;

public interface NewsService {

    NewsDto createNews(NewsRequest request);

    NewsDto updateNews(Long id, NewsRequest request);

    List<NewsDto> getAllNews();

    NewsDto getNewsById(Long id);

    void deleteNews(Long id);
}