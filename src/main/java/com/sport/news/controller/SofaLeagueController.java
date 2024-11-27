package com.sport.news.controller;

import com.sport.news.dto.MatchUpdateRequest;
import com.sport.news.model.Event;
import com.sport.news.model.League;
import com.sport.news.model.Season;
import com.sport.news.model.Standings;
import com.sport.news.model.Team;
import com.sport.news.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sofa")
public class SofaLeagueController {

    @Autowired
    private LeagueService leagueService;
    @Autowired
    private SeasonService seasonService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private StandingsService standingsService;
    @Autowired
    private EventService eventService;

    // League APIs
    @GetMapping("/leagues")
    public List<League> getAllLeagues() {
        return leagueService.getAllLeagues();
    }

    @GetMapping("/leagues/{id}")
    public League getLeagueById(@PathVariable String id) {
        return leagueService.getLeagueById(id);
    }

    @PostMapping("/leagues")
    public ResponseEntity<String> createLeague(@RequestBody League league) {
        leagueService.createLeague(league);
        return new ResponseEntity<>("Created Successfully", HttpStatus.CREATED);
    }

    @PutMapping("/leagues/{id}")
    public League updateLeague(@PathVariable String id, @RequestBody League league) {
        return leagueService.updateLeague(id, league);
    }

    @DeleteMapping("/leagues/{id}")
    public ResponseEntity<String> deleteLeague(@PathVariable String id) {
        leagueService.deleteLeague(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }

    // Season APIs
    @GetMapping("/seasons")
    public List<Season> getAllSeasons() {
        return seasonService.getAllSeasons();
    }


    @GetMapping("/seasons/{seasonId}")
    public Season getSeasonById(@PathVariable String seasonId) {
        return seasonService.getSeasonById(seasonId);
    }


    // Team APIs
    @GetMapping("/teams/{teamId}")
    public Team getTeamById(@PathVariable String teamId) {
        return teamService.getTeamById(teamId);
    }


    @GetMapping("/leagues/{leagueId}/teams")
    public List<Team> getTeamsByLeagueId(@PathVariable String leagueId) {
        return teamService.getTeamByLeagueId(leagueId);
    }

    // Standings APIs
    @GetMapping("/standings/{leagueId}/{seasonId}")
    public Standings getStandings(@PathVariable String leagueId, @PathVariable String seasonId) {
        return standingsService.getStandings(leagueId, seasonId);
    }


    // Event APIs
    @GetMapping("/leagues/{leagueId}/seasons/{seasonId}/matches")
    public List<Event> getMatches(@PathVariable String leagueId, @PathVariable String seasonId) {
        return eventService.getMatches(leagueId, seasonId);
    }

    @GetMapping("/leagues/{leagueId}/seasons/{seasonId}/matches/{round}")
    public List<Event> getMatchesByRound(@PathVariable String leagueId, @PathVariable String seasonId, @PathVariable int round) {
        return eventService.getMatchesByRound(leagueId, seasonId, round);
    }


    @GetMapping("/matches/{eventId}")
    public Event getMatchDetails(@PathVariable String eventId) {
        return eventService.getMatchDetails(eventId);
    }

    @GetMapping("/teams/{teamId}/seasons/{seasonId}/matches")
    public List<Event> getMatchesByTeamAndSeasonId(@PathVariable String teamId, @PathVariable String seasonId) {
        return eventService.getMatchesByTeamAndSeason(seasonId, teamId);
    }

    @PutMapping("/matches/{eventId}")
    public ResponseEntity<String> updateMatch(@PathVariable String eventId, @RequestBody MatchUpdateRequest request) {
        eventService.updateMatch(eventId, request);
        return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
    }
}