package com.javaexpress.service;

import com.javaexpress.dto.PaymentRequestDTO;
import com.javaexpress.dto.PaymentResponseDTO;

public interface PaymentService {

	PaymentResponseDTO processPayment(PaymentRequestDTO request);
	
	// Assignment
	PaymentResponseDTO getPaymentDetails(Long orderId);
}
