package com.space_yellow_duck.miniproject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemResponse {
	private Long id;
	private Long productId;
	private int price;
	private int quantity;
}
