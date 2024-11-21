package com.sport.news.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("comments")
public class Comment {
    
    @Id
    private String id;
    private String content;
    @DBRef
    private User author;
    @DBRef
    private News news;
    private LocalDateTime createdAt;
    private List<User> likes;

}
