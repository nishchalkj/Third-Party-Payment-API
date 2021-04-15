package com.rabo.tppapi.exception;

/**
 * Superclass of all the custom Exception
 * @author Nishchal
 *
 */
public class PaymentInitiationException extends RuntimeException{

	private static final long serialVersionUID = -1578041203365642300L;
	
    private final String message;
	
	private int statusCode;  
	/**
	 * {@link PaymentInitiationException} constructor.
	 *
	 * @param message
	 *            exception message
	 * 
	 */
	public PaymentInitiationException(final String message) {
		this.message = message;
	}
	
	/**
	 * {@link PaymentInitiationException} constructor.
	 *
	 * @param message
	 *            exception message
	 * @param statusCode  
	 *            error statuscode
	 *  
	 */
	public PaymentInitiationException(final String message, final int statusCode) {
		this.message = message;
		this.statusCode = statusCode;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}
	
	
	

}
