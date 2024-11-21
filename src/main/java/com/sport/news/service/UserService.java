package com.sport.news.service;

import java.util.List;

import com.sport.news.model.User;

public interface UserService {

    User createUser(User user);
    List<User> getAllUsers();
    List<User> getActiveUsers();
    User getUserById(String id);
    User updatUser(String id, User user);
    void softDelete(String id);
    void deleteUser(String id);

}
