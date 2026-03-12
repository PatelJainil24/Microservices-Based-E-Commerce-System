package com.javaexpress.feignsclients;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.javaexpress.dto.ProductResponseDTO;
import com.javaexpress.dto.ProductStockUpdateDTO;

@FeignClient(name = "product-service",path="/products")
public interface ProductServiceClient {


    @PutMapping("/update-stock")
    void updateStock(@RequestBody List<ProductStockUpdateDTO> updates);
    
	@GetMapping("/image/{productId}")
    public ProductResponseDTO getProductImage(@PathVariable Long productId);
}

