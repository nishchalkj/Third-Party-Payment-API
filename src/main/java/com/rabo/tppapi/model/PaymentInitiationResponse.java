package com.rabo.tppapi.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is response body class
 * @author Nishchal
 *
 */
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PaymentInitiationResponse {
	
	@EqualsAndHashCode.Include
	private String paymentId;
	
	@EqualsAndHashCode.Include
	private String status;
	
	
	@Override
	public String toString() {
		return "PaymentInitiationResponse [paymentId=" + paymentId + ", status=" + status + "]";
	}
	
	

}
