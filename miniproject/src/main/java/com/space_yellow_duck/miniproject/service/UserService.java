package com.space_yellow_duck.miniproject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.space_yellow_duck.miniproject.Entity.User;
import com.space_yellow_duck.miniproject.Repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	public boolean existsByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isPresent()) return true;
		return false;
	}
	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userRepository.save(user);
	}
	
	
}
