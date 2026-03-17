package com.space_yellow_duck.miniproject.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        System.out.println("유저 있음");
        Users user = userInfo.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // 👉 DB에 암호화된 값
                .roles(user.getRole())
                .build();
    }
}