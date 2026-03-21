package com.space_yellow_duck.miniproject.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.space_yellow_duck.miniproject.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("""
		    SELECT DISTINCT p FROM Product p
		    LEFT JOIN FETCH p.images
		    """)
		    List<Product> findAllWithImages();
}