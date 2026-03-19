package com.space_yellow_duck.miniproject.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.space_yellow_duck.miniproject.Entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long>{
	List<ProductImage> findByProductIdOrderBySortOrder(Long productId);
}
