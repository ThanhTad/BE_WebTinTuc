package com.sport.news.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("news")
public class News {

    @Id
    private String id;
    private String title;
    private String type;
    private String author;
    private String description;
    private String dateTime;
    private LocalDateTime isoTime;
    private List<String> mainText;
    private List<ImgLink> imgLinks;

@Data
public static class ImgLink {

    private String url;
    private String title;
    private String type;

}

}


