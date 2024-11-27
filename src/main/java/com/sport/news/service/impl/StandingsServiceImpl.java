package com.sport.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sport.news.exception.DataNotFoundException;
import com.sport.news.model.Standings;
import com.sport.news.repository.StandingsRepository;
import com.sport.news.service.StandingsService;

@Service
@Transactional
public class StandingsServiceImpl implements StandingsService {

    @Autowired
    private StandingsRepository standingsRepository;

    @Override
    public Standings getStandings(String leagueId, String seasonId) {
        if(leagueId == null || leagueId.isEmpty()){
            throw new IllegalArgumentException("League Id cannot be null or empty");
        }

        if(seasonId == null || seasonId.isEmpty()){
            throw new IllegalArgumentException("Season Id cannot be null or empty");
        }

        return standingsRepository.findByLeagueIdAndSeasonId(leagueId, seasonId)
                    .orElseThrow(() -> new DataNotFoundException("Standing not found"));
    }

}
