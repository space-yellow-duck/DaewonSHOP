package com.space_yellow_duck.miniproject.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {

    private Long id;
    private String name;
    private String imageUrl;
    private List<String> options;
    private int quantity;
    private int totalPrice;
}