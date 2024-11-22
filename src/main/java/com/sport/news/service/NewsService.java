package com.sport.news.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sport.news.model.News;

public interface NewsService {

    Page<News> getAllNews(Pageable pageable);
    Page<News> searchNewsByTitle(String title, Pageable pageable);
    News creatNews(News news);
    News updateNews(String id, News news);
    void deleteNews(String id);
    News getNewsById(String id);

}
