package com.javaexpress.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaexpress.dto.CartItemRequestDTO;
import com.javaexpress.dto.CartItemResponseDTO;
import com.javaexpress.dto.ProductResponseDTO;
import com.javaexpress.dto.UserDto;
import com.javaexpress.feignsclients.ProductServiceClient;
import com.javaexpress.feignsclients.UserFeignClient;
import com.javaexpress.model.CartItem;
import com.javaexpress.repository.CartItemRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private UserFeignClient userFeignClient;
    
    @Autowired
    private UserIntegrationService userIntegrationService;
    
    @Autowired
    private ProductIntegrationService productIntegrationService;

   
    public CartItemResponseDTO addToCart(CartItemRequestDTO request) {
        validateRequest(request);

        // Fetch product and user
        ProductResponseDTO product = productIntegrationService.fetchProductDetails(request.getProductId());
        UserDto user = userIntegrationService.fetchUser(request.getUserId());

        // Check if item already exists in the cart
        Optional<CartItem> existingItemOpt = cartItemRepo.findByUserIdAndProductId(request.getUserId(), request.getProductId());
        log.info("existingItemOpt {} ",existingItemOpt.isPresent());
        CartItem item;

        if (existingItemOpt.isPresent()) {
            // ✅ Item already in cart — update quantity
            item = existingItemOpt.get();
            item.setQuantity(item.getQuantity() + request.getQuantity()); // increase quantity
        } else {
            // ✅ New item — create new CartItem
            item = new CartItem();
            item.setUserId(request.getUserId());
            item.setProductId(request.getProductId());
            item.setQuantity(request.getQuantity());
        }

        // Save and return response
        return mapToResponseDTO(cartItemRepo.save(item), product);
    }


    
    public CartItemResponseDTO updateQuantity(CartItemRequestDTO request) {
        CartItem item = cartItemRepo.findByUserIdAndProductId(request.getUserId(), request.getProductId())
                .orElseThrow(() -> new RuntimeException("Item not in cart"));

        item.setQuantity(request.getQuantity());
        CartItem updatedItem = cartItemRepo.save(item);

        ProductResponseDTO product = productIntegrationService.fetchProductDetails(updatedItem.getProductId());

        return mapToResponseDTO(updatedItem, product);
    }


   
    public List<CartItemResponseDTO> getUserCart(Long userId) {
        return cartItemRepo.findByUserId(userId).stream()
            .map(cart -> {
                ProductResponseDTO product = productIntegrationService.fetchProductDetails(cart.getProductId());
                return mapToResponseDTO(cart, product); // ✅ Return the mapped DTO
            })
            .collect(Collectors.toList());
    }

    
    @Transactional
    public void removeItem(Long userId, Long productId) {
        cartItemRepo.deleteByUserIdAndProductId(userId, productId);
    }

   
    @Transactional
    public void clearCart(Long userId) {
        cartItemRepo.deleteByUserId(userId);
    }

    // ---------------- Helper Methods ----------------

    private void validateRequest(CartItemRequestDTO request) {
        int quantity = request.getQuantity();
        if (quantity <= 0 || quantity > 10) {
            throw new IllegalArgumentException("Quantity must be between 1 and 10");
        }
    }

    private CartItemResponseDTO mapToResponseDTO(CartItem item,ProductResponseDTO product) {
        CartItemResponseDTO dto = new CartItemResponseDTO();
        BeanUtils.copyProperties(item, dto);
        dto.setProduct(product);
        return dto;
    }

    @CircuitBreaker(name = "productServiceCB", fallbackMethod = "productFallback")
    public boolean checkProduct(Long productId) {
        return productServiceClient.isProductExists(productId);
    }

    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "userFallback")
    public UserDto fetchUser(Long userId) {
        return userFeignClient.findById(userId);
    }

    // ---------------- Fallbacks ----------------

    public boolean productFallback(Long productId, Throwable t) {
        System.err.println("Product Service Fallback: " + t.getMessage());
        return false; // Assume product doesn't exist
    }

    public UserDto userFallback(Long userId, Throwable t) {
        System.err.println("User Service Fallback: " + t.getMessage());
        throw new RuntimeException("User service is unavailable. Cannot fetch user info.", t);
    }
}
