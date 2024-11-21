package com.sport.news.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("leagues")
public class League {

    @Id
    private String id;
    private String name;
    private String country;
    private int sofascoreId;

}
