package com.javaexpress.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaexpress.clients.OrderService;
import com.javaexpress.dto.OrderResponseDTO;
import com.javaexpress.dto.ShippingRequestDTO;
import com.javaexpress.dto.ShippingResponseDTO;
import com.javaexpress.models.ShippingInfo;
import com.javaexpress.repository.ShippingInfoRepository;

import jakarta.transaction.Transactional;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingInfoRepository shippingInfoRepository;

    @Autowired
    private OrderService orderService; // To fetch and update order info

    @Override
    @Transactional
    public ShippingResponseDTO shipOrder(Long orderId, ShippingRequestDTO request) {
        // Validate order exists
        OrderResponseDTO order = orderService.getOrderById(orderId);
        if(order == null) {
        	throw new RuntimeException("Order Not Found");
        }

        // Create ShippingInfo entity
        ShippingInfo shippingInfo = new ShippingInfo();
        shippingInfo.setOrderId(orderId);
        shippingInfo.setShippingMethod(request.getShippingMethod());
        shippingInfo.setCarrier(request.getCarrier());
        shippingInfo.setShippedAt(LocalDateTime.now());
        shippingInfo.setStatus("SHIPPED");
        shippingInfo.setTrackingNumber(generateTrackingNumber(orderId));

        ShippingInfo saved = shippingInfoRepository.save(shippingInfo);

        // Optionally update order status
        orderService.updateOrderStatus(orderId, "SHIPPED");

        return mapToDTO(saved);
    }

    @Override
    public ShippingResponseDTO getShippingDetails(Long orderId) {
        ShippingInfo info = shippingInfoRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Shipping info not found for orderId: " + orderId));
        return mapToDTO(info);
    }

    @Override
    @Transactional
    public void updateShippingStatus(Long orderId, String status) {
        ShippingInfo info = shippingInfoRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Shipping info not found for orderId: " + orderId));
        info.setStatus(status);
        info.setDeliveryDate("DELIVERED".equals(status) ? LocalDateTime.now() : null);
        shippingInfoRepository.save(info);

        if ("DELIVERED".equals(status)) {
            orderService.updateOrderStatus(orderId, "DELIVERED");
        }
    }

    

    private String generateTrackingNumber(Long orderId) {
        return "TRACK-" + orderId + "-" + System.currentTimeMillis();
    }

    private String fetchTrackingStatus(String trackingNumber) {
        // Simulate a call to FedEx/UPS API
        return "IN_TRANSIT"; // or "DELIVERED"
    }

    private ShippingResponseDTO mapToDTO(ShippingInfo info) {
        ShippingResponseDTO dto = new ShippingResponseDTO();
        dto.setOrderId(info.getOrderId());
        dto.setCarrier(info.getCarrier());
        dto.setShippingMethod(info.getShippingMethod());
        dto.setTrackingNumber(info.getTrackingNumber());
        dto.setShippedAt(info.getShippedAt());
        dto.setDeliveryDate(info.getDeliveryDate());
        dto.setStatus(info.getStatus());
        return dto;
    }
}


