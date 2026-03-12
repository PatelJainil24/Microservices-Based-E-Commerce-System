package com.javaexpress.product.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaexpress.product.dto.ProductRequestDTO;
import com.javaexpress.product.dto.ProductResponseDTO;

@FeignClient(name="PRODUCT-SERVICE",path="/products")
public interface ProductFeignClient {

	@PostMapping
	public ProductResponseDTO addProduct(@RequestBody ProductRequestDTO request);
	
	@GetMapping("/image/{productId}")
    public ProductResponseDTO getProductImage(@PathVariable Long productId);
	
	@PostMapping(value="/image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> addProduct1(@ModelAttribute ProductRequestDTO request);
	 
	@GetMapping("/search")
    public List<ProductResponseDTO> searchProducts(@RequestParam String keyword);
	
	@GetMapping("/image")
	public ResponseEntity<List<ProductResponseDTO>> fetchAllProducts();
}
