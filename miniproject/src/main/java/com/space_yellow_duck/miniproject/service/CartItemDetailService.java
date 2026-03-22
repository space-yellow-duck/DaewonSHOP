package com.space_yellow_duck.miniproject.service;

import org.springframework.stereotype.Service;

import com.space_yellow_duck.miniproject.Entity.CartItem;
import com.space_yellow_duck.miniproject.Entity.CartItemDetail;
import com.space_yellow_duck.miniproject.Repository.CartItemDetailRepository;

@Service
public class CartItemDetailService {
	private final CartItemDetailRepository cartItemDetailRepository;
	public CartItemDetailService(CartItemDetailRepository cartItemDetailRepository) {
		this.cartItemDetailRepository = cartItemDetailRepository;
	}
	public void deleteDetail(CartItem cartItem) {
		cartItemDetailRepository.deleteAllByCartItem(cartItem);
	}
}
