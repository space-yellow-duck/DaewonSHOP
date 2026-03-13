package com.space_yellow_duck.miniproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.space_yellow_duck.miniproject.Entity.ProductImages;
import com.space_yellow_duck.miniproject.Repository.ProductImageRepository;

@Service
public class ProductImageService {
	private final ProductImageRepository productImagesRepository;
	public ProductImageService(ProductImageRepository productImagesRepository) {
		this.productImagesRepository = productImagesRepository;
	}
	public List<ProductImages> findByProductId(Long productId) {
		return productImagesRepository.findByProductIdOrderBySortOrder(productId);
	}
}
