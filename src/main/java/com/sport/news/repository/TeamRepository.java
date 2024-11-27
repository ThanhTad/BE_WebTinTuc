package com.sport.news.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sport.news.model.Team;

public interface TeamRepository extends MongoRepository<Team, String> {

    List<Team> findByLeagueId(String leagueId);

}
