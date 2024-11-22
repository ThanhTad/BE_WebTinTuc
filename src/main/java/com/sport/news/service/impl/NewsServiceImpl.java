package com.sport.news.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sport.news.model.News;
import com.sport.news.repository.NewsRepository;
import com.sport.news.service.NewsService;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public Page<News> getAllNews(Pageable pageable) {
        try {
            return newsRepository.findAll(pageable);
        } catch (Exception e) {
            log.error("Error fetching all news", e);
            throw new IllegalArgumentException("Failed to fetch all news");
        }
    }

    @Override
    public Page<News> searchNewsByTitle(String title, Pageable pageable) {
        try {
            return newsRepository.findByTitleContainingIgnoreCase(title, pageable);
        } catch (Exception e) {
            log.error("Error searching news by title: {}", title, e);
            throw new IllegalArgumentException("Failed to search news by title: " + title);
        }
    }

    @Override
    public News creatNews(News news) {
        validateInput(news);
        news.setIsoTime(LocalDateTime.now());
        try {
            News savedNews = newsRepository.save(news);
            log.info("News created successfully: {}", savedNews.getId());
            return savedNews;
        } catch (Exception e) {
            log.error("Error creating news", e);
            throw new IllegalArgumentException("Failed to create news");
        }
    }

    @Override
    public News updateNews(String id, News news) {
        validateInput(news);
        return newsRepository.findById(id)
                .map(existingNews -> {
                    updateNewsField(existingNews, news);
                    existingNews.setIsoTime(LocalDateTime.now());
                    return newsRepository.save(existingNews);
                })
                .orElseThrow(() -> new IllegalArgumentException("News not found with id: " + id));
    }

    @Override
    public void deleteNews(String id) {
        News news = getNewsById(id);
        try {
            newsRepository.delete(news);
            log.info("News deleted successfully: {}", id);
        } catch (Exception e) {
            log.error("Error deleting news with id: {}", id, e);
            throw new IllegalArgumentException("Failed to delete news with id: " + id);
        }
    }

    @Override
    public News getNewsById(String id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("News not found with id: " + id));
    }

    private void validateInput(News news) {
        if (news == null) {
            throw new IllegalArgumentException("News cannot be null");
        }

        if (StringUtils.isBlank(news.getTitle())) {
            throw new IllegalArgumentException("News title cannot be empty");
        }

        if (StringUtils.isBlank(news.getAuthor())) {
            throw new IllegalArgumentException("News author cannot be empty");
        }
    }

    private void updateNewsField(News existingNews, News newNews) {
        existingNews.setTitle(newNews.getTitle());
        existingNews.setType(newNews.getType());
        existingNews.setAuthor(newNews.getAuthor());
        existingNews.setDescription(newNews.getDescription());
        existingNews.setMainText(newNews.getMainText());
        existingNews.setImgLinks(newNews.getImgLinks());
    }
}
