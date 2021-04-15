package com.rabo.tppapi.exception;

/**
 * InvalidRequestException custom exception
 * @author Nishchal
 *
 */
public class InvalidRequestException extends PaymentInitiationException {


	private static final long serialVersionUID = 2273438424051976187L;

	/**
	 * {@link InvalidRequestException} constructor.
	 *
	 * @param message
	 *            exception message
	 * 
	 */
	public InvalidRequestException(final String message) {
		super(message);
	}
	
	/**
	 * {@link InvalidRequestException} constructor.
	 *
	 * @param message
	 *            exception message
	 * @param statusCode  
	 *            error statuscode
	 *  
	 */
	public InvalidRequestException(final String message, final int statusCode) {
		super(message,statusCode);
	}

	
}
