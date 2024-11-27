package com.sport.news.service;

import com.sport.news.model.Standings;

public interface StandingsService {

    Standings getStandings(String leagueId, String seasonId);

}
