package com.sport.news.service;

import java.util.List;

import com.sport.news.model.Team;

public interface TeamService {

    Team getTeamById(String teamId);
    List<Team> getTeamByLeagueId(String leagueId);

}
