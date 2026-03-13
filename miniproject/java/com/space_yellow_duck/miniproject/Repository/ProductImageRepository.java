package com.space_yellow_duck.miniproject.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.space_yellow_duck.miniproject.Entity.ProductImages;

public interface ProductImageRepository extends JpaRepository<ProductImages, Long>{
	List<ProductImages> findByProductIdOrderBySortOrder(Long productId);
}
