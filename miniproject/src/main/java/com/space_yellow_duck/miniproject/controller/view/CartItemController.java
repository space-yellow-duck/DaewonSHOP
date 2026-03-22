package com.space_yellow_duck.miniproject.controller.view;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.space_yellow_duck.miniproject.DTO.CartItemDto;
import com.space_yellow_duck.miniproject.DTO.CustomUserDetails;
import com.space_yellow_duck.miniproject.Entity.CartItem;
import com.space_yellow_duck.miniproject.Entity.CartItemDetail;
import com.space_yellow_duck.miniproject.Entity.ProductDetail;
import com.space_yellow_duck.miniproject.service.CartItemService;

@Controller
public class CartItemController {

    private final SecurityFilterChain filterChain;
	private final CartItemService cartItemService;
	public CartItemController(CartItemService cartItemService, SecurityFilterChain filterChain) {
		this.cartItemService = cartItemService;
		this.filterChain = filterChain;
	}
	
	@GetMapping("/cart")
	public String cartPage(@AuthenticationPrincipal CustomUserDetails userDetails,
	                       Model model) {

	    List<CartItemDto> cartItems =
	        cartItemService.getCartItemDtos(userDetails.getUser());

	    int totalPrice = cartItems.stream()
	            .mapToInt(CartItemDto::getTotalPrice)
	            .sum();

	    model.addAttribute("cartItems", cartItems);
	    model.addAttribute("totalPrice", totalPrice);

	    return "cart";
	}
}
