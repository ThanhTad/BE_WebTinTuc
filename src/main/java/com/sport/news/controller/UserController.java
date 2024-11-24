package com.sport.news.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DuplicateKeyException;
import com.sport.news.exception.UserAlreadyExistsException;
import com.sport.news.model.User;
import com.sport.news.service.UserService;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user){
        try {
            System.out.println(user);
            userService.createUser(user);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch(DuplicateKeyException e){
            throw new UserAlreadyExistsException("User with this username or email already exists.");
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<User>> getActiveUsers(){
        List<User> activUsers = userService.getActiveUsers();
        return new ResponseEntity<>(activUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody User user){
        userService.updatUser(id, user);
        return new ResponseEntity<>("Update Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDeleteUser(@PathVariable String id){
        userService.softDelete(id);
        return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/hard/{id}")
    public ResponseEntity<String> hardDeleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);
    } 

}
