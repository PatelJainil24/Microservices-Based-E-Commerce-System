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
@Table(name="payments")
@Setter
@Getter
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String razorpayOrderId; //Razorpay generated Order ID
	private String razorpayPaymentId; // Razorpay payment ID
	
	private Integer amount; 
	private String currency;
	private String status; // CREATED / PAID / FAILED / REFUNDED
	
	private String name;
	private String email;
	
	private Long orderId; // FK to your internal order table
	
	private String errorCode;
	private String errorDescription;
	
	private LocalDateTime payDateTime = LocalDateTime.now();
}
