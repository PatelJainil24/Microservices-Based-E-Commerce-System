package com.javaexpress.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShippingRequestDTO {
    private Long orderId;
    private String shippingMethod; //STANDARD EXPRESS PICKUP TWO_DAY
    private String carrier; // Carrier like FedEx, UPS, etc.
}

