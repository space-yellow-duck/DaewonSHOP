package com.space_yellow_duck.miniproject.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.space_yellow_duck.miniproject.Entity.Users;
import com.space_yellow_duck.miniproject.Repository.UsersRepository;

@Service
public class UsersService {
	private final UsersRepository usersRepository;
	public UsersService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}
	public Optional<Users> findByUsername(String username) {
		Optional<Users> users = usersRepository.findByUsername(username);
		
		
		return users;
	}
}
