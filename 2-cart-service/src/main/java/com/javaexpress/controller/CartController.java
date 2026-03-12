package com.javaexpress.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaexpress.dto.CartItemRequestDTO;
import com.javaexpress.dto.CartItemResponseDTO;
import com.javaexpress.service.CartService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    // POST /cart/add
    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDTO> addItemToCart(@RequestBody CartItemRequestDTO request) {
    	log.info("CartController addItemToCart");
        CartItemResponseDTO response = cartService.addToCart(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // PUT /cart/update
    @PutMapping("/update")
    public ResponseEntity<CartItemResponseDTO> updateCartItem(@RequestBody CartItemRequestDTO request) {
    	log.info("CartController updateCartItem");
        CartItemResponseDTO response = cartService.updateQuantity(request);
        return ResponseEntity.ok(response);
    }

    // GET /cart/{userId}
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemResponseDTO>> getCartByUser(@PathVariable Long userId) {
    	log.info("CartController getCartByUser");
        List<CartItemResponseDTO> cartItems = cartService.getUserCart(userId);
        return ResponseEntity.ok(cartItems);
    }

    // DELETE /cart/remove/{productId}?userId=xx
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long productId,
                                                   @RequestParam Long userId) {
    	log.info("CartController removeItemFromCart");
        cartService.removeItem(userId, productId);
        return ResponseEntity.noContent().build();
    }

    // DELETE /cart/clear/{userId}
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearUserCart(@PathVariable Long userId) {
    	log.info("CartController clearUserCart");
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}

