package com.javaexpress.dto;

import java.math.BigDecimal;

import com.javaexpress.model.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProductStockUpdateDTO {
    private Long productId;
    private Integer quantity; // Reduce this much
}

