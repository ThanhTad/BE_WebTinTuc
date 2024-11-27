package com.sport.news.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sport.news.model.Season;

public interface SeasonRepository extends MongoRepository<Season, String> {

}
