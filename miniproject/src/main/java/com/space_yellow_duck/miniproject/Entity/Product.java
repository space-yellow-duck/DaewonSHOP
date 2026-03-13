package com.space_yellow_duck.miniproject.Entity;

import java.util.List;

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
    private Long id;

    private String name;
    private String category;
    private String subCategory;
    private String fit;
    private String color;
    private String size;
    private int price;
    private int stock;
    private String description;
    private String imageUrl;
    
    @OneToMany(mappedBy = "product")
    private List<ProductImages> images;
}