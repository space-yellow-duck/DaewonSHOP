package com.space_yellow_duck.miniproject.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;
    private String fit;
    private int price;
    private String description;
    
    
    @OneToMany(mappedBy = "product")
    private List<ProductImage> image;
    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItem;
}