package com.space_yellow_duck.miniproject.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.space_yellow_duck.miniproject.Entity.CartItem;
import com.space_yellow_duck.miniproject.Entity.Product;
import com.space_yellow_duck.miniproject.Entity.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	Optional<CartItem> findByUsersAndProduct(User user, Product product);
	List<CartItem> findAllByUsers(User user);
}
