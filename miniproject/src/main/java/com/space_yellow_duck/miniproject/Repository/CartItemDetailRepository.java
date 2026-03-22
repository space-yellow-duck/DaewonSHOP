package com.space_yellow_duck.miniproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.space_yellow_duck.miniproject.Entity.CartItemDetail;
import com.space_yellow_duck.miniproject.Entity.CartItem;


public interface CartItemDetailRepository extends JpaRepository<CartItemDetail, Long>{
	void deleteAllByCartItem(CartItem cartItem);
}
