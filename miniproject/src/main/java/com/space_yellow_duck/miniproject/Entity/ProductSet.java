package com.space_yellow_duck.miniproject.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProductSet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_set_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product; // 세트

	@ManyToOne
	@JoinColumn(name = "child_product_id")
	private Product childProduct; // 구성품

	private int quantity;
}
