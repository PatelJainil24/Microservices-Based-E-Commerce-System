package com.javaexpress.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShippingResponseDTO {
    private Long orderId;
    private String trackingNumber;
    private String shippingMethod;
    private String status;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveryDate;
    private String carrier;
}

