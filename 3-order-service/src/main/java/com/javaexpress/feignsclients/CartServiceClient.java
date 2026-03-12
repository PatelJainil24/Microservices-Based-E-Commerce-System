package com.javaexpress.feignsclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.javaexpress.dto.CartItemDTO;

@FeignClient(name = "cart-service",path="/cart")
public interface CartServiceClient {

    @GetMapping("/{userId}")
    List<CartItemDTO> getCart(@PathVariable Long userId);

    @DeleteMapping("/clear/{userId}")
    void clearCart(@PathVariable Long userId);
}

