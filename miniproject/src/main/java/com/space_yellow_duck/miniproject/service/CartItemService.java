package com.space_yellow_duck.miniproject.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.space_yellow_duck.miniproject.DTO.CartItemDto;
import com.space_yellow_duck.miniproject.DTO.CartItemRequest;
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
	public boolean addCartItem(User user, List<CartItemRequest> items) {
		
		SaleType type = items.get(0).getType();

		boolean allSame = items.stream()
		        .allMatch(i -> i.getType() == type);

		if (!allSame) {
		    throw new IllegalArgumentException("혼합 타입 요청은 허용되지 않습니다.");
		}
		switch (type) {
		case SINGLE:
			for (CartItemRequest item : items) {
				addSingleItem(item,user);
			}
			break;
		case MULTI_PACK:
			for(CartItemRequest item : items) {
				addMultiPackItem(user, item);
			}
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
	
	
	public void addSingleItem(CartItemRequest item,User user) {

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
	
	public void addMultiPackItem(User user, CartItemRequest request) {// 1️⃣ 정렬 (중복 포함 유지)
	    List<Long> sortedIds = new ArrayList<>(request.getDetailIds());
	    Collections.sort(sortedIds);

	    // 2️⃣ 기존 장바구니 조회
	    List<CartItem> cartItems = cartItemRepository.findAllByUser(user);

	    // 3️⃣ 동일 조합 찾기
	    for (CartItem item : cartItems) {

	        if (item.getSaleType() != SaleType.MULTI_PACK) continue;

	        List<Long> existingIds = item.getDetails().stream()
	                .map(d -> d.getProductDetail().getId())
	                .sorted()
	                .toList();

	        if (existingIds.equals(sortedIds)) {
	            item.setQuantity(item.getQuantity() + request.getQuantity());
	            return;
	        }
	    }

	    // 4️⃣ 새 CartItem 생성
	    CartItem cartItem = new CartItem();
	    cartItem.setUser(user);
	    cartItem.setSaleType(SaleType.MULTI_PACK);
	    cartItem.setQuantity(request.getQuantity());

	    // 🔥 5️⃣ ID → ProductDetail Map으로 조회 (중복 대응 핵심)
	    List<ProductDetail> productDetails =
	            productDetailRepository.findAllById(
	                    new HashSet<>(sortedIds) // 중복 제거해서 조회
	            );

	    Map<Long, ProductDetail> detailMap = productDetails.stream()
	            .collect(Collectors.toMap(ProductDetail::getId, d -> d));

	    // 🔥 6️⃣ 원본 ID 리스트 기준으로 생성 (중복 유지)
	    for (Long id : sortedIds) {
	        ProductDetail pd = detailMap.get(id);

	        if (pd == null) {
	            throw new IllegalArgumentException("상품 없음: " + id);
	        }

	        CartItemDetail detail = new CartItemDetail();
	        detail.setProductDetail(pd);
	        cartItem.addDetail(detail);
	    }

	    // 7️⃣ 저장
	    cartItemRepository.save(cartItem);
	}
}
