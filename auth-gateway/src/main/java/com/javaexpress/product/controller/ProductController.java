package com.javaexpress.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaexpress.product.dto.ProductRequestDTO;
import com.javaexpress.product.dto.ProductResponseDTO;
import com.javaexpress.product.service.ProductFeignClient;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductFeignClient productFeignClient;

    // 1. Add product using JSON
    @PostMapping
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO request) {
        log.info("Auth Gateway - Add Product (JSON)");
        ProductResponseDTO response = productFeignClient.addProduct(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. Add product with image (multipart/form-data)
    @PostMapping("/image")
    public ResponseEntity<String> addProductWithImage(@ModelAttribute ProductRequestDTO request) {
        log.info("Auth Gateway - Add Product with Image (Multipart)");
        return productFeignClient.addProduct1(request);
    }

    // 3. Get product by ID (for image or details)
    @GetMapping("/image/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductImage(@PathVariable Long productId) {
        log.info("Auth Gateway - Get Product Image for ID: {}", productId);
        ProductResponseDTO product = productFeignClient.getProductImage(productId);
        return ResponseEntity.ok(product);
    }

    // 4. Fetch all products
    @GetMapping("/image")
    public ResponseEntity<List<ProductResponseDTO>> fetchAllProducts() {
        log.info("Auth Gateway - Fetch All Products");
        return productFeignClient.fetchAllProducts();
    }

    // 5. Search products by keyword
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@RequestParam String keyword) {
        log.info("Auth Gateway - Search Products by Keyword: {}", keyword);
        List<ProductResponseDTO> results = productFeignClient.searchProducts(keyword);
        return ResponseEntity.ok(results);
    }
}

