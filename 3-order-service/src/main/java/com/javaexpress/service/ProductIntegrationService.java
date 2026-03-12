package com.javaexpress.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaexpress.dto.ProductResponseDTO;
import com.javaexpress.feignsclients.ProductServiceClient;

@Service
public class ProductIntegrationService {

    @Autowired
    private ProductServiceClient productServiceClient;
   
    public ProductResponseDTO fetchProductDetails(Long productId) {
    	return productServiceClient.getProductImage(productId);
    }
}
