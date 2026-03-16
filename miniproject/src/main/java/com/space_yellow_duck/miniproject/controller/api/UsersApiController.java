package com.space_yellow_duck.miniproject.controller.api;

import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.space_yellow_duck.miniproject.Entity.Users;
import com.space_yellow_duck.miniproject.model.PasswordValidater;
import com.space_yellow_duck.miniproject.service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersApiController {
	private final UsersService usersService;
	public UsersApiController(UsersService usersService) {
		this.usersService = usersService;
	}
	@GetMapping("/check-username")
	public Map<String,Boolean> checkUsername(@RequestParam String username ) {
		boolean exists = usersService.findByUsername(username);
		return Map.of("available", !exists);
	}
	@PostMapping("/signup")
	public Map signup(Users users) {
		System.out.println(users.getUsername()+users.getPassword());
		PasswordValidater passwordValidater = new PasswordValidater();
		passwordValidater.validate(users.getPassword());
		users.setRole("USER");
		if(usersService.findByUsername(users.getUsername())) throw new IllegalArgumentException("이미 있는 아이디입니다 새로운 아이디로 다시 시도해주세요.");
		usersService.save(users);
		return Map.of("success",true);
	}
}
