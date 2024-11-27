package com.sport.news.service;

import java.util.List;

import com.sport.news.model.Season;

public interface SeasonService {

    List<Season> getAllSeasons();
    Season getSeasonById(String seasonId);

}
