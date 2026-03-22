package com.space_yellow_duck.miniproject.DTO;

import java.util.List;

import com.space_yellow_duck.miniproject.Entity.Product;
import com.space_yellow_duck.miniproject.Entity.SaleType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PuttedItem {

    private SaleType type; // SINGLE, MULTI_PACK, SET

    private Long productDetailId; // SINGLE용

    private List<Long> detailIds; // MULTI_PACK용

    private Integer quantity;
}
