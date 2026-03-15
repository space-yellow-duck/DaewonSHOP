package com.space_yellow_duck.miniproject.controller.view;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.space_yellow_duck.miniproject.Entity.Product;
import com.space_yellow_duck.miniproject.Entity.ProductImages;
import com.space_yellow_duck.miniproject.service.ProductImageService;
import com.space_yellow_duck.miniproject.service.ProductService;

@Controller
public class ProductController {

    private final ProductService productService;
    private final ProductImageService productImagesService;

    public ProductController(ProductService productService,ProductImageService productImagesService) {
        this.productService = productService;
        this.productImagesService = productImagesService;
    }

    @GetMapping("/products/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        List<ProductImages> images = productImagesService.findByProductId(product.getId());
        model.addAttribute("images",images);
        model.addAttribute("product", product);

        return "product/detail";
    }
}
