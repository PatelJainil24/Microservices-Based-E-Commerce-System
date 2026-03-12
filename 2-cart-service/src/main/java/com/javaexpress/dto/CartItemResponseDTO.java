package com.javaexpress.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemResponseDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private LocalDateTime addedAt;
    private String message;
    private ProductResponseDTO product;
}
