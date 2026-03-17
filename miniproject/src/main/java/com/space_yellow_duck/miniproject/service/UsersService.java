package com.space_yellow_duck.miniproject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.space_yellow_duck.miniproject.Entity.Users;
import com.space_yellow_duck.miniproject.Repository.UsersRepository;

@Service
public class UsersService {
	private final UsersRepository usersRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public UsersService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}
	public boolean existsByUsername(String username) {
		Optional<Users> users = usersRepository.findByUsername(username);
		if(users.isPresent()) return true;
		return false;
	}
	public Users save(Users users) {
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		
		return usersRepository.save(users);
	}
	
	
}
