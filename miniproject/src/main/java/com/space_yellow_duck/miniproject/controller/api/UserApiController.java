package com.space_yellow_duck.miniproject.controller.api;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.space_yellow_duck.miniproject.Entity.User;
import com.space_yellow_duck.miniproject.model.PasswordValidater;
import com.space_yellow_duck.miniproject.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserApiController {
	private final UserService userService;
	public UserApiController(UserService userService) {
		this.userService = userService;
	}
	@GetMapping("/check-username")
	public Map<String,Boolean> checkUsername(@RequestParam String username ) {
		boolean exists = userService.existsByUsername(username);
		return Map.of("available", !exists);
	}
	@PostMapping("/signup")
	public boolean signup(User user) {
		PasswordValidater passwordValidater = new PasswordValidater();
		passwordValidater.validate(user.getPassword());
		user.setRole("USER");
		if(userService.existsByUsername(user.getUsername())) throw new IllegalArgumentException("이미 있는 아이디입니다 새로운 아이디로 다시 시도해주세요.");
		userService.save(user);
		return true;
	}
}
