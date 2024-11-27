package com.sport.news.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sport.news.model.Standings;

public interface StandingsRepository extends MongoRepository<Standings, String> {

    Optional<Standings> findByLeagueIdAndSeasonId(String leagueId, String seasonId);

}
