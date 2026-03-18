package com.space_yellow_duck.miniproject.controller.api;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.space_yellow_duck.miniproject.DTO.CustomUserDetails;
import com.space_yellow_duck.miniproject.DTO.PuttedItem;
import com.space_yellow_duck.miniproject.DTO.CartItemResponse;
import com.space_yellow_duck.miniproject.Entity.CartItem;
import com.space_yellow_duck.miniproject.Entity.Product;
import com.space_yellow_duck.miniproject.service.CartItemService;
import com.space_yellow_duck.miniproject.service.CustomUserDetailsService;

@RestController
public class CartItemApiController {
	private final CartItemService cartItemService;
	public CartItemApiController(CartItemService cartItemService) {
		this.cartItemService = cartItemService;
	}
	@PostMapping("/api/cart-items")
	public boolean putCartItem(@RequestBody PuttedItem item,@AuthenticationPrincipal CustomUserDetails userDetails) {
		return cartItemService.addCartItem(userDetails.getUser(), item);
		
	}
	@PatchMapping("/api/cart-items/{id}")
	@ResponseBody
	public CartItemResponse updateQuantity(@PathVariable Long id,
	                           @RequestBody Map<String, Integer> body) {

	    int diff = body.get("diff");
	    CartItemResponse response = new CartItemResponse();
	    CartItem cartItem = cartItemService.updateQuantity(id, diff);
	    response.setId(cartItem.getId());
	    response.setProductId(cartItem.getProduct().getId());
	    response.setPrice(cartItem.getProduct().getPrice());
	    response.setQuantity(cartItem.getQuantity());
	    return response;
	}
	@DeleteMapping("/api/cart-items/{id}")
	public void deleteItem(@PathVariable Long id) {
		cartItemService.delete(id);
	}
	
	
}
