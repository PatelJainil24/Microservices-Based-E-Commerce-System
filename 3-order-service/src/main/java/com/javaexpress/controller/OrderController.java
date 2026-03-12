package com.javaexpress.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaexpress.dto.OrderResponseDTO;
import com.javaexpress.dto.PlaceOrderRequestDTO;
import com.javaexpress.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService checkoutService;

    @PostMapping
    public OrderResponseDTO placeOrder(@RequestBody PlaceOrderRequestDTO request) {
    	log.info("OrderController placeOrder");
        OrderResponseDTO response = checkoutService.placeOrder(request);
        return response;
    }

    //// mandatory to implement
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long orderId) {
    	log.info("OrderController getOrder");
        return ResponseEntity.ok(checkoutService.getOrderById(orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUser(@PathVariable Long userId) {
    	log.info("OrderController getOrdersByUser");
        return ResponseEntity.ok(checkoutService.getOrdersByUser(userId));
    }
    
    // mandatory to implement
    @PutMapping("/{orderId}/status/{status}")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long orderId,
                                                  @PathVariable String status) {
    	log.info("OrderController updateOrderStatus");
    	checkoutService.updateOrderStatus(orderId, status);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
    
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        log.info("OrderController getAllOrders");
        return ResponseEntity.ok(checkoutService.getAllOrders());
    }
    
}

