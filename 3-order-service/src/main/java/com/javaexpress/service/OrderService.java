package com.javaexpress.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.javaexpress.dto.OrderResponseDTO;
import com.javaexpress.dto.PlaceOrderRequestDTO;
import com.javaexpress.model.OrderStatus;

public interface OrderService {
   
	OrderResponseDTO placeOrder(PlaceOrderRequestDTO request);     // Create an order
	OrderResponseDTO getOrderById(Long orderId);                   // View order details
	List<OrderResponseDTO> getOrdersByUser(Long userId);           // Get all orders by user
	void updateOrderStatus(Long orderId, String status);           // Change order status
	//void cancelOrder(Long orderId);                                // Cancel the order
	List<OrderResponseDTO> getAllOrders();
	
//	List<OrderResponseDTO> getOrdersByStatus(String status);
//	List<OrderResponseDTO> getOrdersBetweenDates(LocalDateTime start, LocalDateTime end);
//	List<OrderResponseDTO> getAllOrders();  // For admin
//	
//	BigDecimal getTotalRevenue();
//	int getOrderCount();
//	Map<String, Long> getOrderCountByStatus();
//	
//	void updatePaymentStatus(Long orderId, String status);  // PaymentStatus enum (PENDING, SUCCESS, FAILED)
//	void initiateRefund(Long orderId);

}

