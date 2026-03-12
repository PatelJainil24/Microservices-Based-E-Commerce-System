package com.javaexpress.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PaymentResponseDTO {

	private Long paymentId;
	private Long orderId;
	private String name;
	private String email;
	private BigDecimal amount;
	private String status; // SUCCESS,FAILED,PENDING
	private LocalDateTime payDateTime;
}
