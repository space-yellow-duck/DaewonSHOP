package com.space_yellow_duck.miniproject.service;

import org.springframework.stereotype.Service;

import com.space_yellow_duck.miniproject.Repository.CartItemRepository;

@Service
public class CartItemService {
	private final CartItemRepository cartItemRepository;
	public CartItemService(CartItemRepository cartItemRepository) {
		this.cartItemRepository = cartItemRepository;
	}
}
