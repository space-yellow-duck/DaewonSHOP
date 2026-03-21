package com.space_yellow_duck.miniproject.controller.view;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.space_yellow_duck.miniproject.Entity.Product;
import com.space_yellow_duck.miniproject.Entity.ProductDetail;
import com.space_yellow_duck.miniproject.Entity.ProductImage;
import com.space_yellow_duck.miniproject.service.ProductDetailService;
import com.space_yellow_duck.miniproject.service.ProductImageService;
import com.space_yellow_duck.miniproject.service.ProductService;

import tools.jackson.databind.ObjectMapper;

@Controller
public class ProductController {

    private final ProductService productService;
    private final ProductImageService productImagesService;
    private final ProductDetailService productDetailService;
    public ProductController(ProductService productService,ProductImageService productImagesService, ProductDetailService productDetailService) {
        this.productService = productService;
        this.productImagesService = productImagesService;
        this.productDetailService = productDetailService;
    }

    @GetMapping("/products/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        List<ProductImage> images = productImagesService.findByProductId(product.getId());
        List<ProductDetail> details = productDetailService.getDetails(product);
        Map<String, List<ProductDetail>> groupedDetails =
                details.stream()
                        .collect(Collectors.groupingBy(ProductDetail::getColor));
        ObjectMapper objectMapper = new ObjectMapper();
        String groupedJson = objectMapper.writeValueAsString(groupedDetails);

        model.addAttribute("groupedJson", groupedJson);
        model.addAttribute("groupedDetails", groupedDetails);
        model.addAttribute("images",images);
        model.addAttribute("product", product);
        model.addAttribute("details",details);
        model.addAttribute("saleType", product.getSaleType());
        model.addAttribute("packQuantity", product.getPackQuantity());
        return "product/detail";
    }
}
