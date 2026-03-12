package com.javaexpress.service;

import java.util.List;

import com.javaexpress.dto.CartItemRequestDTO;
import com.javaexpress.dto.CartItemResponseDTO;

public interface CartService {
    CartItemResponseDTO addToCart(CartItemRequestDTO request);
    CartItemResponseDTO updateQuantity(CartItemRequestDTO request);
    List<CartItemResponseDTO> getUserCart(Long userId);
    void removeItem(Long userId, Long productId);
    void clearCart(Long userId);
}

