package com.space_yellow_duck.miniproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.space_yellow_duck.miniproject.DTO.PuttedItem;
import com.space_yellow_duck.miniproject.Entity.CartItem;
import com.space_yellow_duck.miniproject.Entity.Product;
import com.space_yellow_duck.miniproject.Entity.ProductDetail;
import com.space_yellow_duck.miniproject.Entity.User;
import com.space_yellow_duck.miniproject.Repository.CartItemRepository;
import com.space_yellow_duck.miniproject.Repository.ProductDetailRepository;
import com.space_yellow_duck.miniproject.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CartItemService {
	private final CartItemRepository cartItemRepository;
	private final UserRepository userRepository;
	private final ProductDetailRepository productDetailRepository;

	public CartItemService(CartItemRepository cartItemRepository, UserRepository userRepository,
			ProductDetailRepository productDetailRepository) {
		this.cartItemRepository = cartItemRepository;
		this.userRepository = userRepository;
		this.productDetailRepository = productDetailRepository;
	}

	public List<CartItem> getCartItems(User user) {

		return cartItemRepository.findAllByUser(user);
	}

	@Transactional
	public boolean addCartItem(User user, PuttedItem item) {

		Optional<ProductDetail> productDetail = productDetailRepository.findById(item.getProductDetailId());
		if (productDetail.isEmpty()) {
			throw new IllegalArgumentException("존재하지 않는 상품입니다.");
		}
		Optional<CartItem> optional = cartItemRepository.findByUserAndProductDetail(user, productDetail.get());

		if (optional.isPresent()) {
			// 이미 있음 → 수량 증가
			CartItem cartItem = optional.get();
			cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());

		} else {
			// 없음 → 새로 생성
			CartItem cartItem = new CartItem();
			cartItem.setUser(user);
			cartItem.setProductDetail(productDetail.get());
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
