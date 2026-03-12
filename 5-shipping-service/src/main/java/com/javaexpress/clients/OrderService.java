package com.javaexpress.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import com.javaexpress.dto.OrderResponseDTO;


@FeignClient(name = "order-service", path="/orders") 
public interface OrderService {

    @PutMapping("/{orderId}/status/{status}")
    void updateOrderStatus(@PathVariable Long orderId, @PathVariable String status);

    // Add this method to get order details
    @GetMapping("/{orderId}")
    OrderResponseDTO getOrderById(@PathVariable Long orderId);
}
