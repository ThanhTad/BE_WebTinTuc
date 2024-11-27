package com.sport.news.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sport.news.model.League;

public interface LeagueRepository extends MongoRepository<League, String> {

}
