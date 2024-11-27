package com.sport.news.service;

import java.util.List;

import com.sport.news.dto.MatchUpdateRequest;
import com.sport.news.model.*;

public interface EventService {

    List<Event> getMatches(String leagueId, String seasonId);
    List<Event> getMatchesByRound(String leagueId, String seasonId, int round);
    Event getMatchDetails(String eventId);
    List<Event> getMatchesByTeamAndSeason(String teamId, String seasonId);
    void updateMatch(String eventId, MatchUpdateRequest request);

}
