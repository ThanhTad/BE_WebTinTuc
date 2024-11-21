package com.sport.news.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("events")
public class Event {

    @Id
    private String id;
    @DBRef
    private League leagueId;
    @DBRef
    private Season seasonId;
    private int round;
    @DBRef
    private Team homeTeamId;
    @DBRef
    private Team awayTeamId;
    private long startTimestamp;
    private String status;
    private int homeScore;
    private int awayScore;
    private int sofascoreId;

}
