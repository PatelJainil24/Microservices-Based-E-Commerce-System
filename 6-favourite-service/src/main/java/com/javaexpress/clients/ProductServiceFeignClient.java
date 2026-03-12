package com.javaexpress.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.javaexpress.dtos.ProductResponseDTO;

@FeignClient(name = "product-service", path="/products")  // Adjust the URL accordingly
public interface ProductServiceFeignClient {

    // Get product details by productId
    @GetMapping("/{productId}")
    ProductResponseDTO getProduct(@PathVariable Integer productId);
}

