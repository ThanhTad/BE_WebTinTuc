package com.sport.news.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sport.news.dto.MatchUpdateRequest;
import com.sport.news.exception.BusinessOperationException;
import com.sport.news.exception.DataNotFoundException;
import com.sport.news.model.Event;
import com.sport.news.repository.EventRepository;
import com.sport.news.service.EventService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> getMatches(String leagueId, String seasonId) {
        if (leagueId == null || leagueId.isEmpty()) {
            throw new IllegalArgumentException("League ID cannot be null or empty");
        }

        if (seasonId == null || seasonId.isEmpty()) {
            throw new IllegalArgumentException("Season ID cannot be null or empty");
        }

        return eventRepository.findByLeagueIdAndSeasonId(leagueId, seasonId)
                    .filter(events -> !events.isEmpty())
                    .orElseThrow(() -> new DataNotFoundException("No matches found for league and season "));
        
    }

    @Override
    public List<Event> getMatchesByRound(String leagueId, String seasonId, int round) {
        if (leagueId == null || leagueId.isEmpty()) {
            throw new IllegalArgumentException("League ID cannot be null or empty");
        }

        if (seasonId == null || seasonId.isEmpty()) {
            throw new IllegalArgumentException("Team ID cannot be null or empty");
        }

        if (round == 0) {
            throw new IllegalArgumentException("Round cannot equal 0");
        }

        return eventRepository.findByLeagueIdAndSeasonIdAndRound(leagueId, seasonId, round)
                    .filter(events -> !events.isEmpty())
                    .orElseThrow(() -> new DataNotFoundException("Matches not found"));
    }

    @Override
    public Event getMatchDetails(String eventId) {
        if (eventId == null || eventId.isEmpty()) {
            throw new IllegalArgumentException("Event ID cannot be null or empty");
        }
        return eventRepository.findById(eventId)
            .orElseThrow(() -> new DataNotFoundException("Match not found"));
    }

    @Override
    public List<Event> getMatchesByTeamAndSeason(String teamId, String seasonId) {
        if (teamId == null || teamId.isEmpty()) {
            throw new IllegalArgumentException("Team ID cannot be null or empty");
        }
        if (seasonId == null || seasonId.isEmpty()) {
            throw new IllegalArgumentException("Season ID cannot be null or empty");
        }

        return eventRepository.findBySeasonIdAndHomeTeamIdOrSeasonIdAndAwayTeamId(seasonId, teamId, seasonId, teamId)
                    .filter(events -> !events.isEmpty())
                    .orElseThrow(() -> new DataNotFoundException("Matches not found"));
    }

    @Override
    public void updateMatch(String eventId, MatchUpdateRequest request) {
        if (eventId == null || eventId.isEmpty()) {
            throw new IllegalArgumentException("Event ID cannot be null or empty");
        }
        try {
            eventRepository.findByIdAndUpdate(
                eventId, 
                request.getHomeScore(), 
                request.getAwayScore(), 
                request.getStatus());
        } catch (Exception e) {
            log.info("Failed to update match with ID: " + eventId, e);
            throw new BusinessOperationException("Failed to update");
        }
    }

}
