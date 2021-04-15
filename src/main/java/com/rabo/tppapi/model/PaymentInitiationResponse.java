package com.rabo.tppapi.model;

import org.springframework.stereotype.Component;

/**
 * This is response body class
 * @author Nishchal
 *
 */
@Component
public class PaymentInitiationResponse {
	
	private String paymentId;
	
	private String status;
	
	/**
	 * @return the paymentId
	 */
	public String getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PaymentInitiationResponse [paymentId=" + paymentId + ", status=" + status + "]";
	}
	
	

}
