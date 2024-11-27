package com.sport.news.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sport.news.exception.DataNotFoundException;
import com.sport.news.model.Team;
import com.sport.news.repository.TeamRepository;
import com.sport.news.service.TeamService;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public Team getTeamById(String teamId) {
        if(teamId == null || teamId.isEmpty()){
            throw new IllegalArgumentException("Team Id cannot be null or empty");
        }

         return teamRepository.findById(teamId)
                    .orElseThrow(() -> new DataNotFoundException("Team not found"));
    }

    @Override
    public List<Team> getTeamByLeagueId(String leagueId) {
        if(leagueId == null || leagueId.isEmpty()){
            throw new IllegalArgumentException("League Id cannot be null or empty");
        }
        return teamRepository.findByLeagueId(leagueId);
    }

}
