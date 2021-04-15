package com.rabo.tppapi.model;

/**
 * PaymentInitiationResponseOverview for the API
 * @author Nishchal
 *
 */
public class PaymentInitiationResponseOverview {
	
    private String responseSignature;
	
	private String certificate;
	
	private PaymentInitiationResponse response;

	/**
	 * @return the responseSignature
	 */
	public String getResponseSignature() {
		return responseSignature;
	}

	/**
	 * @param responseSignature the responseSignature to set
	 */
	public void setResponseSignature(String responseSignature) {
		this.responseSignature = responseSignature;
	}

	/**
	 * @return the certificate
	 */
	public String getCertificate() {
		return certificate;
	}

	/**
	 * @param certificate the certificate to set
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	/**
	 * @return the response
	 */
	public PaymentInitiationResponse getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(PaymentInitiationResponse response) {
		this.response = response;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PaymentInitiationResponseOverview [responseSignature=" + responseSignature + ", certificate="
				+ certificate + ", response=" + response + "]";
	}
	
	
	

}


