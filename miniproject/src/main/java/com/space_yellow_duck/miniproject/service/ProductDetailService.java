package com.space_yellow_duck.miniproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.space_yellow_duck.miniproject.Entity.Product;
import com.space_yellow_duck.miniproject.Entity.ProductDetail;
import com.space_yellow_duck.miniproject.Repository.ProductDetailRepository;

@Service
public class ProductDetailService{
	private final ProductDetailRepository productDetailRepository;
	public ProductDetailService(ProductDetailRepository productDetailRepository) {
		this.productDetailRepository = productDetailRepository;
	}
	public List<ProductDetail> getDetails(Product product){
		return productDetailRepository.findAllByProduct(product);
	}
    
    
}
