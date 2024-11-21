package com.sport.news.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("standings")
public class Standings {

    @Id
    private String id;
    @DBRef
    private League leagueId;
    @DBRef
    private Season seasonId;
    private List<StandingEntry> standings;

@Data
public static class StandingEntry {

    @DBRef
    private Team teamId;
    private int position;
    private int played;
    private int won;
    private int drawn;
    private int lost;
    private int goalFors;
    private int goalAgainst;
    private int goalDifference;
    private int points;
}

}
