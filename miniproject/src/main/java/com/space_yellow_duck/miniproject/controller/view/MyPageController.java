package com.space_yellow_duck.miniproject.controller.view;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.space_yellow_duck.miniproject.DTO.CustomUserDetails;
import com.space_yellow_duck.miniproject.Entity.Address;
import com.space_yellow_duck.miniproject.Entity.User;
import com.space_yellow_duck.miniproject.service.AddressService;


@Controller
@RequestMapping("/mypage")
public class MyPageController {
	private final AddressService addressService;
	public MyPageController(AddressService addressService) {
		this.addressService = addressService;
	}
	
	@GetMapping
	public String mypage(@AuthenticationPrincipal CustomUserDetails userDetails,
	                     Model model) {

	    User user = userDetails.getUser();

	    // TODO: 서비스에서 가져오기
	    model.addAttribute("user", user);
	    model.addAttribute("orderCount", 0);
	    model.addAttribute("shippingCount", 0);
	    model.addAttribute("wishlistCount", 0);
	    model.addAttribute("points", 0);
	    model.addAttribute("recentOrders", List.of());

	    return "mypage/mypage";
	}
	
	@GetMapping("/profile")
	public String profile(@AuthenticationPrincipal CustomUserDetails userDetails,Model model) {
		User user = userDetails.getUser();
		
		model.addAttribute("user",user);
		
		return "mypage/profile";
	}
	
	@GetMapping("/profile/address")
	public String addressManagePage(@AuthenticationPrincipal CustomUserDetails userDetails,Model model) {
		List<Address> addresses = addressService.getAddresses(userDetails.getUser());
		model.addAttribute("addresses",addresses);
		
		return "mypage/address";
	}
	
	@GetMapping("/address/new")
	public String addAddressPage(@AuthenticationPrincipal CustomUserDetails userDetails,Model model) {
		
		return "mypage/newAddress";
	}
}
