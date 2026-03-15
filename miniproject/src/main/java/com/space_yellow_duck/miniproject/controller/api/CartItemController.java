package com.space_yellow_duck.miniproject.controller.api;

import org.springframework.web.bind.annotation.RestController;

import com.space_yellow_duck.miniproject.service.CartItemService;

@RestController
public class CartItemController {
	private final CartItemService cartItemService;
	public CartItemController(CartItemService cartItemService) {
		this.cartItemService = cartItemService;
	}
	
}
