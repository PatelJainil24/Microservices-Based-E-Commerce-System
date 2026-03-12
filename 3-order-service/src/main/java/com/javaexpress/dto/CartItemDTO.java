package com.javaexpress.dto;

import java.math.BigDecimal;

import com.javaexpress.model.Order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemDTO {
    private Long productId;
    private Integer quantity;
}

