package com.sport.news.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.DuplicateKeyException;
import com.sport.news.exception.InvalidUserInformationException;
import com.sport.news.exception.UserAlreadyExistsException;
import com.sport.news.model.User;
import com.sport.news.repository.UserRepository;
import com.sport.news.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 

    @Override
    public User createUser(User user) {
        checkUsernameUnique(user.getUsername());
        checkEmailUnique(user.getEmail());
        if(user.getPassword() == null || user.getPassword().isEmpty()){
            throw new InvalidUserInformationException("Password cannot be null or empty");
        }

        String hashPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPass);
        user.setIsDeleted(false);
        user.setRole("user");
        try {
            User savedUser = userRepository.save(user);
            log.info("User created successfully: {}", savedUser.getUsername());
            return savedUser;
        } catch (DuplicateKeyException e) {
            log.error("Failed to create user: {}", e.getMessage());
            throw new UserAlreadyExistsException("User with this username or email already exists.");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getActiveUsers() {
        return userRepository.findByIsDeleted(false);
    }

    @Override
    public User getUserById(String id) {
       return userRepository.findById(id)
                            .filter(user -> !user.getIsDeleted())
                            .orElseThrow(() -> new UsernameNotFoundException("User not found or has been deleted"));
    }

    @Override
    public User updatUser(String id, User user) {
        if(user == null || id == null){
            throw new InvalidUserInformationException("User or User ID cannot be null");
        }
        checkUsernameUnique(user.getUsername());
        checkEmailUnique(user.getEmail());
        return userRepository.findById(id)
                                .map(existingUser -> {
                                    existingUser.setUsername(user.getUsername());
                                    existingUser.setEmail(user.getEmail());
                                    return userRepository.save(existingUser);
                                })
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void softDelete(String id) {
        User user = userRepository.findById(id)
                                    .orElseThrow(() -> new UsernameNotFoundException("User ID does not exist"));
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
       if(!userRepository.existsById(id)){
            throw new UsernameNotFoundException("User ID does not exist");
       }
       userRepository.deleteById(id);
    }


    public void checkUsernameUnique(String username) {
        if (userRepository.existsByUsernameAndIsDeletedFalse(username)) {
            throw new UserAlreadyExistsException("Username already exists");
        }
    }

    public void checkEmailUnique(String email) {
        if (userRepository.existsByEmailAndIsDeletedFalse(email)) {
            throw new UserAlreadyExistsException("Email already exists");
        }
    }

}
