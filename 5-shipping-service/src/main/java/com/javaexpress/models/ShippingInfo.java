package com.javaexpress.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "shipping_info")
@Setter
@Getter
public class ShippingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long orderId;

    private String trackingNumber;
    private String shippingMethod; // Standard, Expedited, Next-Day
    private LocalDateTime shippedAt;
    private LocalDateTime deliveryDate;
    private String status; // SHIPPED, IN_TRANSIT, DELIVERED, etc.
    private String carrier; // FedEx, UPS, DHL
}

