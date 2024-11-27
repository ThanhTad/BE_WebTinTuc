package com.sport.news.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sport.news.exception.BusinessOperationException;
import com.sport.news.exception.DataNotFoundException;
import com.sport.news.model.League;
import com.sport.news.repository.LeagueRepository;
import com.sport.news.service.LeagueService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class LeagueServiceImpl implements LeagueService {
    
    @Autowired
    private LeagueRepository leagueRepository;

    @Override
    public List<League> getAllLeagues() {
        return leagueRepository.findAll();
    }

    @Override
    public League getLeagueById(String leagueId) {
        if (leagueId == null || leagueId.isEmpty()) {
            throw new IllegalArgumentException("League ID cannot be empty");
        }
         return leagueRepository.findById(leagueId)
                    .orElseThrow(() -> new DataNotFoundException("League not found"));

    }

    @Override
    public League createLeague(League league) {
        if (league == null) {
            throw new IllegalArgumentException("League data cannot be null");
        }
        if (league.getName() == null || league.getName().isEmpty()) {
            throw new IllegalArgumentException("League name cannot be empty");
        }

        try {
            return leagueRepository.save(league);
        } catch (Exception e) {
            log.error("Error creating league: {}", e.getMessage());
            throw new BusinessOperationException("Failed to create league", e);
        }
    }

    @Override
    public League updateLeague(String leagueId, League league) {
        if (leagueId == null || leagueId.isEmpty()) {
            throw new IllegalArgumentException("League ID cannot be empty");
        }
        if (league == null) {
            throw new IllegalArgumentException("League data cannot be null");
        }
        if (league.getName() == null || league.getName().isEmpty()) {
            throw new IllegalArgumentException("League name cannot be empty");
        }

        return leagueRepository.findById(leagueId)
                .map(existingLeague -> {
                    existingLeague.setName(league.getName());
                    existingLeague.setCountry(league.getCountry());
                    try {
                        return leagueRepository.save(existingLeague);
                    } catch (Exception e) {
                        log.error("Error updating league: {}", e.getMessage());
                        throw new BusinessOperationException("Failed to update league", e);
                    }
                })
                .orElseThrow(() -> new DataNotFoundException("League not found with id: " + leagueId));
    }

    @Override
    public void deleteLeague(String leagueId) {
        if (leagueId == null || leagueId.isEmpty()) {
            throw new IllegalArgumentException("League ID cannot be empty");
        }

        try {
            leagueRepository.deleteById(leagueId);
        } catch (Exception e) {
            log.error("Error deleting league: {}", e.getMessage());
            throw new BusinessOperationException("Failed to delete league", e);
        }
    }

    

}
