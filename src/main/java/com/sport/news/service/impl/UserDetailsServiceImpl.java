package com.sport.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sport.news.model.User;
import com.sport.news.model.UserPrincipal;
import com.sport.news.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserPrincipal.build(user);
                            
    }

}
