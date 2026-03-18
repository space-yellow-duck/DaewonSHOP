package com.space_yellow_duck.miniproject.DTO;

import com.space_yellow_duck.miniproject.Entity.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PuttedItem {
	private Long productId;
	private int quantity;
}
