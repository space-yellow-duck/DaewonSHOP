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
	public Map signup(@RequestBody Users users) {
		boolean exists = usersService.findByUsername(users.getUsername());
		if(exists) return Map.of("success",!exists,"error_msg","이미 존재하는 로그인 아이디입니다 다른 아이디로 가입해주세요.");
		return Map.of("success",true);
	}
}
