package com.javaexpress.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentFailureRequest {

	private String errorCode;
	private String errorDescription;
	private String razorpayOrderId;
	private String razorpayPaymentId;
	private String name;
	private String email;
	private Integer orderId;
	private String amount;
}
