package com.javaexpress.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaexpress.dto.ProductResponseDTO;
import com.javaexpress.feignsclients.ProductServiceClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ProductIntegrationService {

    @Autowired
    private ProductServiceClient productServiceClient;

    @CircuitBreaker(name = "productServiceCB", fallbackMethod = "productFallback")
    public boolean checkProduct(Long productId) {
        return productServiceClient.isProductExists(productId);
    }

    public boolean productFallback(Long productId, Throwable t) {
        System.err.println("Product Service Fallback: " + t.getMessage());
        return false; // Assume product doesn't exist
    }
    
    public ProductResponseDTO fetchProductDetails(Long productId) {
    	return productServiceClient.getProductImage(productId);
    }
}
