package com.javaexpress.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.javaexpress.dto.ProductRequestDTO;
import com.javaexpress.dto.ProductResponseDTO;
import com.javaexpress.dto.ProductStockUpdateDTO;
import com.javaexpress.models.Product;

public interface ProductService {
    ProductResponseDTO addProduct(ProductRequestDTO request);
    ProductResponseDTO getProductById(Long productId);
    BigDecimal getPriceById(Long productId);
    List<ProductResponseDTO> getAllProducts();
    void updateStock(List<ProductStockUpdateDTO> updates);
    public boolean isProductExists(Long productId);
    void addProductImage(ProductRequestDTO request);
    ProductResponseDTO getProductByIdImage(Long productId);
    List<ProductResponseDTO> getAllProductsWithImages();
    public List<ProductResponseDTO> searchProducts(String keyword);
	public void editProductImage(Long productId, ProductRequestDTO request);
	List<ProductResponseDTO> getAllProductsFilter(Specification<Product> spec);
}

