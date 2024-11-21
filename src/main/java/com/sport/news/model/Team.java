package com.sport.news.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("teams")
public class Team {

    @Id
    private String id;
    private String name;
    @DBRef
    private League leagueId;
    private String logo;
    private String type;
    private int sofascoreId;

}
