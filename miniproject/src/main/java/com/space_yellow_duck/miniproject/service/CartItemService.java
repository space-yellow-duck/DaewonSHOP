package com.space_yellow_duck.miniproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.space_yellow_duck.miniproject.DTO.CartItemDto;
import com.space_yellow_duck.miniproject.DTO.PuttedItem;
import com.space_yellow_duck.miniproject.Entity.CartItem;
import com.space_yellow_duck.miniproject.Entity.CartItemDetail;
import com.space_yellow_duck.miniproject.Entity.Product;
import com.space_yellow_duck.miniproject.Entity.ProductDetail;
import com.space_yellow_duck.miniproject.Entity.SaleType;
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
	public CartItem getCartItem(Long id) {
		Optional<CartItem> cartItem = cartItemRepository.findById(id);
		if(cartItem.isEmpty()) {
			throw new IllegalArgumentException("해당 장바구니 아이템은 존재하지 않습니다");
		}
		return cartItem.get();
	}
	public List<CartItemDto> getCartItemDtos(User user) {

	    List<CartItem> cartItems = cartItemRepository.findAllByUser(user);

	    return cartItems.stream().map(item -> {

	        CartItemDto dto = new CartItemDto();

	        dto.setId(item.getId());
	        dto.setQuantity(item.getQuantity());

	        // 상품 정보 (첫 detail 기준)
	        Product product = item.getDetails().get(0)
	                .getProductDetail().getProduct();

	        dto.setName(product.getName());
	        dto.setImageUrl(product.getImages().get(0).getImageUrl());

	        // 옵션 리스트
	        List<String> options = item.getDetails().stream()
	                .map(d -> d.getProductDetail().getColor()
	                        + " / " + d.getProductDetail().getSize())
	                .toList();

	        dto.setOptions(options);

	        // 가격 계산
	        int itemPrice = item.getDetails().stream()
	                .mapToInt(d -> d.getProductDetail().getProduct().getPrice())
	                .sum();

	        dto.setTotalPrice(itemPrice * item.getQuantity());

	        return dto;

	    }).toList();
	}

	@Transactional
	public boolean addCartItem(User user, List<PuttedItem> items) {
		
		SaleType type = items.get(0).getType();

		boolean allSame = items.stream()
		        .allMatch(i -> i.getType() == type);

		if (!allSame) {
		    throw new IllegalArgumentException("혼합 타입 요청은 허용되지 않습니다.");
		}
		switch (type) {
		case SINGLE:
			for (PuttedItem item : items) {
				addSingleItem(item,user);
			}
			break;
		case MULTI_PACK:
			
			break;
		case SET:
			break;
		default:
			break;
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
	public void delete(CartItem cartItem) {
		cartItemRepository.delete(cartItem);
	}
	
	
	public void addSingleItem(PuttedItem item,User user) {

	    CartItem cartItem = new CartItem();
	    cartItem.setSaleType(SaleType.SINGLE);
	    cartItem.setQuantity(item.getQuantity());

	    // ⭐ ID → 엔티티 조회
	    ProductDetail pd = productDetailRepository
	            .findById(item.getProductDetailId())
	            .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

	    CartItemDetail detail = new CartItemDetail();
	    detail.setProductDetail(pd);
	    cartItem.setUser(user);

	    // ⭐ 핵심: 양방향 연결
	    cartItem.addDetail(detail);

	    cartItemRepository.save(cartItem);
	}
}
