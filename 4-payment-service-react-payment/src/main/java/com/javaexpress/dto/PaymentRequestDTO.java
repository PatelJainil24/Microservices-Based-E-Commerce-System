package com.javaexpress.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentRequestDTO {

	private Long orderId;
	private String name;
	private String email;
	private Integer amount;

}
