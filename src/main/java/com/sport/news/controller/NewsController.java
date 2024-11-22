package com.sport.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sport.news.model.News;
import com.sport.news.service.NewsService;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    
    @Autowired
    private NewsService newsService;

    @GetMapping
    public ResponseEntity<Page<News>> getAllNews(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(newsService.getAllNews(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<News>> searchNewsByTitle(
        @RequestParam String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(newsService.searchNewsByTitle(title, pageable));
    }

    @PostMapping
    public ResponseEntity<String> createNews(@RequestBody News news){
        News createdNews = newsService.creatNews(news);
        return ResponseEntity.status(HttpStatus.CREATED).body("News created successfully: " + createdNews.getTitle());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateNews(
        @PathVariable String id, 
        @RequestBody News news
    ) {
        News updatedNews = newsService.updateNews(id, news);
        return ResponseEntity.ok("News updated successfully: " + updatedNews.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable String id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok("News deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable String id) {
        News news = newsService.getNewsById(id);
        return ResponseEntity.ok(news);
    }

}
