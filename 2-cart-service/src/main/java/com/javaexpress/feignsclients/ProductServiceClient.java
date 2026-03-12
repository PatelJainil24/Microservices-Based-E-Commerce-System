package com.javaexpress.feignsclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.javaexpress.dto.ProductResponseDTO;

@FeignClient(name = "product-service",path="/products")
public interface ProductServiceClient {
    
	@GetMapping("/exists/{productId}")
    boolean isProductExists(@PathVariable("productId") Long productId);
	
	@GetMapping("/image/{productId}")
    public ProductResponseDTO getProductImage(@PathVariable Long productId);
}

