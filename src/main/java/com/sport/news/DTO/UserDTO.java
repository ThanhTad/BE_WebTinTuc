package com.sport.news.DTO;

import lombok.Data;

@Data
public class UserDTO {

    private String id;
    private String username;
    private String email;
    private String role;
    private Boolean isDeleted;
    
}
