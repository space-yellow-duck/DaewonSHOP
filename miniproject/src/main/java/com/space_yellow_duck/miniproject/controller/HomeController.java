package com.space_yellow_duck.miniproject.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.space_yellow_duck.miniproject.Entity.Product;
import com.space_yellow_duck.miniproject.service.ProductService;

@Controller
public class HomeController {
	 private final ProductService productService;

	    public HomeController(ProductService productService) {
	        this.productService = productService;
	    }
    @GetMapping("/")
    public String home(Model model){
        List<Product> products = productService.findTopProducts();

        model.addAttribute("products", products);

        return "home";
    }
}