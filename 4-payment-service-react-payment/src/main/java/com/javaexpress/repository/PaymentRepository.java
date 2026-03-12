package com.javaexpress.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaexpress.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

	Payment findByRazorpayOrderId(String razroOrderId);
	
	Optional<Payment> findByOrderId(Long orderId);
}
