package com.sport.news.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sport.news.model.News;

public interface NewsRepository extends MongoRepository<News, String> {

    Page<News> findAll(Pageable pageable);
    Page<News> findByTitleContainingIgnoreCase(String title, Pageable pageable);

}
