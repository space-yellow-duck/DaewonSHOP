package com.space_yellow_duck.miniproject.controller.view;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.space_yellow_duck.miniproject.service.UserService;


@Controller
public class UserController {
	private final UserService usersService;
	public UserController(UserService usersService) {
		this.usersService = usersService;
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	@GetMapping("/signup")
	public String signupPage() {
		return "signup";
	}
	
}
