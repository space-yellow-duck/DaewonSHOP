package com.space_yellow_duck.miniproject.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.space_yellow_duck.miniproject.service.UsersService;


@Controller
public class UsersController {
	private final UsersService usersService;
	public UsersController(UsersService usersService) {
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
