package com.sport.news.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String details;

    public ErrorResponse(LocalDateTime timestamp, int status, String message, String details){
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.details = details;
    }

}
