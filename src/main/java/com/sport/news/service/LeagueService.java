package com.sport.news.service;

import java.util.List;

import com.sport.news.model.League;

public interface LeagueService {

    List<League> getAllLeagues();
    League getLeagueById(String leagueId);
    League createLeague(League league);
    League updateLeague(String id, League league);
    void deleteLeague(String id);
    
}
