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
}
