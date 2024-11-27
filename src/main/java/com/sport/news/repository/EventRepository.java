package com.sport.news.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.sport.news.model.Event;

public interface EventRepository extends MongoRepository<Event, String> {

    Optional<List<Event>> findByLeagueIdAndSeasonId(String leagueId, String seasonId);
    Optional<List<Event>> findByLeagueIdAndSeasonIdAndRound(String leagueId, String seasonId, int round);
    Optional<List<Event>> findBySeasonIdAndHomeTeamIdOrSeasonIdAndAwayTeamId(
        String seasonId, String homeTeamId, String seasonId2, String awayTeamId
    );

    @Query("{'_id': ?0}")
    @Update("{ $set: { 'homeScore' : ?1, 'awayScore': ?2, 'status': ?3 } }")
    void findByIdAndUpdate(String eventId, int homeScore, int awayScore, String status);

}
