package com.space_yellow_duck.miniproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.space_yellow_duck.miniproject.DTO.PuttedItem;
import com.space_yellow_duck.miniproject.Entity.CartItem;
import com.space_yellow_duck.miniproject.Entity.Product;
import com.space_yellow_duck.miniproject.Entity.Users;
import com.space_yellow_duck.miniproject.Repository.CartItemRepository;
import com.space_yellow_duck.miniproject.Repository.ProductRepository;
import com.space_yellow_duck.miniproject.Repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class CartItemService {
	private final CartItemRepository cartItemRepository;
	private final UsersRepository usersRepository;
	private final ProductRepository productRepository;

	public CartItemService(CartItemRepository cartItemRepository, UsersRepository usersRepository,
			ProductRepository productRepository) {
		this.cartItemRepository = cartItemRepository;
		this.usersRepository = usersRepository;
		this.productRepository = productRepository;
	}

	public List<CartItem> getCartItems(Users user) {

		return cartItemRepository.findAllByUsers(user);
	}

	@Transactional
	public boolean addCartItem(Users user, PuttedItem item) {

		Optional<Product> product = productRepository.findById(item.getProductId());
		if (product.isEmpty()) {
			throw new IllegalArgumentException("존재하지 않는 상품입니다.");
		}
		Optional<CartItem> optional = cartItemRepository.findByUsersAndProduct(user, product.get());

		if (optional.isPresent()) {
			// 이미 있음 → 수량 증가
			CartItem cartItem = optional.get();
			cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());

		} else {
			// 없음 → 새로 생성
			CartItem cartItem = new CartItem();
			cartItem.setUsers(user);
			cartItem.setProduct(product.get());
			cartItem.setQuantity(item.getQuantity());

			CartItem saved = cartItemRepository.save(cartItem);
			if (saved.getId() != null) {
				return true;
			}
		}
		return false;
	}

	@Transactional
	public CartItem updateQuantity(Long id, int diff) {
		Optional<CartItem> cartItem = cartItemRepository.findById(id);
		if(cartItem.isEmpty()) {
			throw new IllegalArgumentException("장바구니에 해당 상품은 존재하지 않습니다.");
		}
		CartItem item = cartItem.get();
		item.setQuantity(item.getQuantity() + diff);
		return item;
	}
	
	@Transactional
	public void delete(Long id) {
		cartItemRepository.deleteById(id);
	}
}
