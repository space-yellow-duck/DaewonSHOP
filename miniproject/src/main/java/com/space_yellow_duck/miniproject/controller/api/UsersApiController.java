package com.space_yellow_duck.miniproject.controller.api;

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
	public boolean checkUsername(@RequestParam String username ) {
		Optional<Users> users = usersService.findByUsername(username);
		if(users.isPresent()) {
			return true;
		}
		return false;
	}
}
