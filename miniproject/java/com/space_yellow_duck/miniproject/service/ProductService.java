package com.space_yellow_duck.miniproject.service;


import org.springframework.stereotype.Service;

import com.space_yellow_duck.miniproject.Entity.Product;
import com.space_yellow_duck.miniproject.Repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

	private final ProductRepository productRepository;
    

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> findTopProducts() {
    	
    	List<Product> list = productRepository.findAll();
    	
    	
    	return list;
    }
    public Product findById(Long id) {
    	
    	return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. id=" + id)); 
    }
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
}