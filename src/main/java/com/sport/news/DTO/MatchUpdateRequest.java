package com.sport.news.dto;

import lombok.Data;

@Data
public class MatchUpdateRequest {

    private Integer homeScore;
    private Integer awayScore;
    private String status;

}
