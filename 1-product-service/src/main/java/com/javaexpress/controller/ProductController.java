package com.javaexpress.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaexpress.dto.ProductRequestDTO;
import com.javaexpress.dto.ProductResponseDTO;
import com.javaexpress.dto.ProductStockUpdateDTO;
import com.javaexpress.models.Product;
import com.javaexpress.service.ProductService;
import com.javaexpress.service.ProductSpecification;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @PostMapping(value="/image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addProduct1(@ModelAttribute ProductRequestDTO request) {
    	log.info("ProductController addProduct");
    	productService.addProductImage(request);
        return ResponseEntity.ok("Product saved successfully!");
    }
    
//    @PutMapping(value="/image/{productId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> updateProduct(@ModelAttribute ProductRequestDTO request,@PathVariable Long productId) {
//    	log.info("ProductController updateProduct");
//    	productService.editProductImage(productId,request);
//        return ResponseEntity.ok("Product Updated successfully!");
//    }

    @PutMapping(value="/image/{productId}")
    public ResponseEntity<String> updateProduct(@RequestBody ProductRequestDTO request,@PathVariable Long productId) {
        log.info("ProductController updateProduct");
        productService.editProductImage(productId,request);
        return ResponseEntity.ok("Product Updated successfully!");
    }

    @GetMapping("/image/{productId}")
    public ProductResponseDTO getProductImage(@PathVariable Long productId) {
    	log.info("ProductController getProduct");
        return productService.getProductByIdImage(productId);
    }
    
    @GetMapping("/image")
    public ResponseEntity<List<ProductResponseDTO>> fetchAllProducts() {
        List<ProductResponseDTO> productList = productService.getAllProductsWithImages();
        return ResponseEntity.ok(productList);
    }
    
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
    	log.info("ProductController getAllProducts");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("/update-stock")
    public ResponseEntity<Void> updateProductStock(@RequestBody List<ProductStockUpdateDTO> updates) {
    	log.info("ProductController updateProductStock");
        productService.updateStock(updates);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/search")
    public List<ProductResponseDTO> searchProducts(@RequestParam String keyword) {
        return productService.searchProducts(keyword);
    }
    
    @GetMapping("/filter")
    public List<ProductResponseDTO> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) String brand
    ) {
        Specification<Product> spec = ProductSpecification.filterByAttributes(name, category, price, available, brand);
        List<ProductResponseDTO> products = productService.getAllProductsFilter(spec);
        return products;
    }
    
}

