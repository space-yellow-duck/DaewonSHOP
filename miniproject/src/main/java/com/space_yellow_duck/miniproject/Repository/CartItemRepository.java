package com.space_yellow_duck.miniproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.space_yellow_duck.miniproject.Entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
