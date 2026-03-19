package com.space_yellow_duck.miniproject.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_detail_id")
	private Long id;
	
	private int quantity;
	private int price;
	private String deliveryStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_detail_id")
	private ProductDetail productDetail;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="orders_id")
	private Orders orders;
}
