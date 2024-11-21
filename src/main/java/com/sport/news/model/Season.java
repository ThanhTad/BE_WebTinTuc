package com.sport.news.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("seasons")
public class Season {

    @Id
    private String id;
    @DBRef
    private League leagueId;
    private String name;
    private String year;
    private int sofascoreId;

}
