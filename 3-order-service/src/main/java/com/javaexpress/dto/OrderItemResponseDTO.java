package com.javaexpress.dto;

import java.math.BigDecimal;

import com.javaexpress.model.Order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemResponseDTO {
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
    private ProductResponseDTO product;
}

