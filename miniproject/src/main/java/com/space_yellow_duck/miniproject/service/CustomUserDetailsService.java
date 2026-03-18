package com.space_yellow_duck.miniproject.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.space_yellow_duck.miniproject.DTO.CustomUserDetails;
import com.space_yellow_duck.miniproject.Entity.Users;
import com.space_yellow_duck.miniproject.Repository.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        Optional<Users> userInfo = usersRepository.findByUsername(username);
        
        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("유저 없음");
        }
        Users user = userInfo.get();
        user.setAddress("USER");
        return new CustomUserDetails(user);
    }
    
}