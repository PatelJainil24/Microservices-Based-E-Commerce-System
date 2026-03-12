package com.javaexpress.service;

import com.javaexpress.dto.ShippingRequestDTO;
import com.javaexpress.dto.ShippingResponseDTO;

public interface ShippingService {
    ShippingResponseDTO shipOrder(Long orderId, ShippingRequestDTO request);
    ShippingResponseDTO getShippingDetails(Long orderId);
    void updateShippingStatus(Long orderId, String status);
}

