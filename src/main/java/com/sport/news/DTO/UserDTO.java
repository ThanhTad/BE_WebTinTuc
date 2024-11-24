package com.sport.news.dto;

import lombok.Data;

@Data
public class UserDTO {

    private String id;
    private String username;
    private String email;
    private String role;
    private Boolean isDeleted;
    
}
