package com.javaexpress.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaexpress.dto.PaymentFailureRequest;
import com.javaexpress.dto.PaymentRequestDTO;
import com.javaexpress.models.Payment;
import com.javaexpress.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
//@CrossOrigin(origins = "*")
public class PaymentController {

	private final String RAZORPAY_KEY = "rzp_test_l1o6qKkq2Arljp";
	private final String RAZORPAY_SECRET = "lHGpoOoSnHCoSdUCLFJdLINu";

	@Autowired
	private PaymentRepository paymentRepository;

	@PostMapping("/create_order")
	@ResponseBody
	public String createOrder(@RequestBody PaymentRequestDTO requestDto) {
		try {
			log.info("payment request dto {}", requestDto);

			RazorpayClient razorpay = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);

			JSONObject options = new JSONObject();
			options.put("amount", requestDto.getAmount() * 100); // amount in paisa
			options.put("currency", "INR");
			options.put("receipt", "txn_" + System.currentTimeMillis());

			Order order = razorpay.orders.create(options);

			Payment payment = new Payment();
			payment.setName(requestDto.getName());
			payment.setEmail(requestDto.getEmail());
			payment.setOrderId(requestDto.getOrderId());
			payment.setAmount(order.get("amount"));
			payment.setCurrency(order.get("currency"));
			payment.setStatus(order.get("status"));
			payment.setRazorpayOrderId(order.get("id"));

			paymentRepository.save(payment);

			return order.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"error\":\"Order creation failed\"}";
		}
	}

	@PostMapping("/payment_success")
	public String handlePaymentSuccess(@RequestParam String razorpay_payment_id,
			@RequestParam String razorpay_order_id,
			@RequestParam int amount) {
		Payment payment = paymentRepository.findByRazorpayOrderId(razorpay_order_id);
		if (payment != null) {
			payment.setRazorpayPaymentId(razorpay_payment_id);
			payment.setStatus("PAID");
			paymentRepository.save(payment);
		}
		return "Payment recorded";
	}
	
	@PostMapping("/payment_failed")
    public String handlePaymentFailure(@RequestBody PaymentFailureRequest request) {
        // Save failure info to DB
		Payment payment = paymentRepository.findByRazorpayOrderId(request.getRazorpayOrderId());
		if (payment != null) {
			payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
			payment.setErrorCode(request.getErrorCode());
			payment.setErrorDescription(request.getErrorDescription());
			payment.setStatus("FAILED");
			paymentRepository.save(payment);
		}
        return "Failure recorded successfully";
    }
}
