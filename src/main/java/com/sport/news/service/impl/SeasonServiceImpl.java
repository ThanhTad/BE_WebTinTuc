package com.sport.news.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sport.news.exception.DataNotFoundException;
import com.sport.news.model.Season;
import com.sport.news.repository.SeasonRepository;
import com.sport.news.service.SeasonService;

@Service
@Transactional
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    private SeasonRepository seasonRepository;

    @Override
    public List<Season> getAllSeasons() {
        return seasonRepository.findAll();
    }

    @Override
    public Season getSeasonById(String seasonId) {
        if(seasonId == null || seasonId.isEmpty()){
            throw new IllegalArgumentException("Season Id cannot be null or empty");
        }
        
        return seasonRepository.findById(seasonId)
                    .orElseThrow(() -> new DataNotFoundException("Season not found"));
    }

}
