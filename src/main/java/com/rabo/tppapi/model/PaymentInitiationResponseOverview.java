package com.rabo.tppapi.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PaymentInitiationResponseOverview for the API
 * @author Nishchal
 *
 */
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInitiationResponseOverview {
	
	@EqualsAndHashCode.Include
    private String responseSignature;
	
	@EqualsAndHashCode.Include
	private String certificate;
	
	@EqualsAndHashCode.Include
	private PaymentInitiationResponse response;


	@Override
	public String toString() {
		return "PaymentInitiationResponseOverview [responseSignature=" + responseSignature + ", certificate="
				+ certificate + ", response=" + response + "]";
	}
	
	
	

}


